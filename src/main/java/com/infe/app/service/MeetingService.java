package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.MeetingRequestDto;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long insertMeeting(AdminRequestDto dto) throws IllegalArgumentException {
        Optional<Meeting> target = meetingRepository.findMeetingByPasskey(dto.getPasskey());
        target.ifPresent(val -> {
            throw new IllegalArgumentException("이미 존재하는 출석키 입니다.");
        }); //출석키 중복확인

        Meeting meeting = Meeting.builder()
                .passkey(dto.getPasskey())
                .lat(dto.getLat())
                .lon(dto.getLon())
                .createdDateTime(dto.getStartTime())
                .endDateTime(dto.getEndTime())
                .build();
        return meetingRepository.save(meeting).getId();
    }

    @Transactional
    public Long isExistKey(AdminRequestDto dto) throws IllegalArgumentException {
        Meeting meeting = meetingRepository.findMeetingByPasskey(dto.getPasskey())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));
        return meeting.getId();
    }

    @Transactional
    public Long insertAttendee(StudentSaveRequestDto dto) throws IllegalArgumentException, TimeoutException,NullPointerException {
       try {
           //passkey 확인
           Meeting meeting = meetingRepository.findMeetingByPasskey(dto.getPasskey())
                   .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));

           //회원 확인
           Member member = memberRepository.findByStudentId(dto.getStudentId()).
                   orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
           log.info("dto="+dto.getGeneration()+", "+dto.getName()+"/member="+ member.getGeneration()+", "+member.getName());
           if(!((dto.getGeneration().equals(member.getGeneration())) && (dto.getName().equals(member.getName())))){
               throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
           }

           //회원 중복 확인
           if (meeting.getMembers().contains(member)) {
               throw new IllegalArgumentException("이미 출석되었습니다.");
           }

           //출석키 만료 확인
           LocalDateTime meetingStart = meeting.getCreatedDateTime();
           LocalDateTime meetingEnd = meeting.getEndDateTime();
           LocalDateTime inputTime = dto.getDateTime();
           if (!(inputTime.isAfter(meetingStart) && inputTime.isBefore(meetingEnd)))
               throw new TimeoutException("출석키가 만료되었습니다.");

           //위치 확인
           if (!(Double.compare(dto.getLat(), meeting.getLat()) == 0 && Double.compare(dto.getLon(), meeting.getLon()) == 0)) {
               throw new IllegalArgumentException("출석 위치가 다릅니다.");
           }

           //출석체크처리
           meeting.addMember(member); //Meeting-Member 서로 추가해줌
           return member.getId();
       }
       catch(NullPointerException e){
           throw new NullPointerException("입력하지 않은 항목이 있습니다.");
       }
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMembersByDate(LocalDateTime dateTime) {
        return meetingRepository.findMembersByDate(dateTime).stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberMeetingResponseDto> findAllMember() {
        return meetingRepository.findAllMember();
    }

    @Transactional
    public Long deleteByDate(MeetingRequestDto dto) throws IllegalArgumentException {
        Meeting meeting = meetingRepository.findMeetingsByCreatedDateTime(dto.getDateTime())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));
        Long id = meeting.getId();

        List<Member> members = meetingRepository.findMembersByDate(dto.getDateTime());
        memberRepository.deleteAll(members);
        meetingRepository.delete(meeting);
        return id;
    }

}
