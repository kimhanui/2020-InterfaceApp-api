package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.Meeting.MeetingRequestDto;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long insertMeeting(AdminRequestDto dto) throws IllegalArgumentException{
        Optional<Meeting> target = meetingRepository.findMeetingByPasskey(dto.getPasskey());
        target.ifPresent(val-> {throw new IllegalArgumentException("이미 존재하는 출석키 입니다.");}); //출석키 중복확인

        Meeting meeting = Meeting.builder()
                                .passkey(dto.getPasskey())
                                .createdDateTime(dto.getStartTime())
                                .endDateTime(dto.getEndTime())
                                .build();
        return meetingRepository.save(meeting).getId();
    }

    @Transactional
    public Long isExistKey(AdminRequestDto dto){
        Meeting meeting = meetingRepository.findMeetingByPasskey(dto.getPasskey())
            .orElseThrow (()-> new IllegalArgumentException("찾는 모임이 없습니다."));
        return meeting.getId();
    }

    @Transactional
    public Long insertAttendee(StudentSaveRequestDto dto) throws TimeoutException { //연관관계 편의 메소드: 사용하기

        //passkey확인(반드시 한개만 나오므로)
        Meeting meeting = meetingRepository.findMeetingByPasskey(dto.getPasskey())
                .orElseThrow(()->new IllegalArgumentException("출석하려는 모임이 없습니다."));

        //dateTime확인
        LocalDateTime meetingStart = meeting.getCreatedDateTime();
        LocalDateTime meetingEnd = meeting.getEndDateTime();
        LocalDateTime inputTime = dto.getDateTime();
        Member member =null;

        if( inputTime.isAfter(meetingStart) && inputTime.isBefore(meetingEnd)){
            member = dto.toMember();
            meeting.addMember(member);
            memberRepository.save(member);
        }
        else{
            throw new TimeoutException();
        }
        return member.getId();
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMembersByDate(LocalDateTime dateTime) {
        return meetingRepository.findMembersByDate(dateTime).stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberMeetingResponseDto> findAllMember(){
        return meetingRepository.findAllMember();
    }

    @Transactional
    public void deleteByDate(MeetingRequestDto dto) throws IllegalArgumentException {
        Meeting meeting = meetingRepository.findMeetingsByCreatedDateTime(dto.getDateTime())
                .orElseThrow(() -> new IllegalArgumentException("찾는 모임이 없습니다."));

        try {
            List<Member> members = meetingRepository.findMembersByDate(dto.getDateTime());

            for (Member m : members) { //연관관계정리
                m.getMeetings().remove(meeting);
            }
            memberRepository.deleteAll(members);
            meetingRepository.delete(meeting);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

}
