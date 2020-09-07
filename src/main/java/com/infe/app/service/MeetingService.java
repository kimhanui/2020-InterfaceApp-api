package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long insertMeeting(AdminRequestDto dto) { //출석키 중복확인
        Meeting meeting = Meeting.builder()
                                .passkey(dto.getPassKey())
                                .createdDateTime(dto.getStartTime())
                                .endDateTime(dto.getEndTime())
                                .build();
        return meetingRepository.save(meeting).getId();
    }

    @Transactional
    public Long isMeetingExist(AdminRequestDto dto){
        Meeting meeting = meetingRepository.findMeetingByPasskey(dto.getPassKey())
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
    public List<MemberResponseDto> findAllMemberDateDesc(LocalDateTime dateTime) {
        return meetingRepository.findAllMemberDesc(dateTime).stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberMeetingResponseDto> findAllMember(){
        return meetingRepository.findAllMember();
    }

    @Transactional
    public void deleteAllByDate(LocalDateTime dateTime){
        Meeting meeting = meetingRepository.findMeetingByCreatedDateTime(dateTime)
                .orElseThrow(()-> new IllegalArgumentException("찾는 모임이 없습니다."));

        List<Member> members = meetingRepository.findAllMemberDesc(dateTime);
        memberRepository.deleteAll(members);
        meetingRepository.delete(meeting);
    }

}
