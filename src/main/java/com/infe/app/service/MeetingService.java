package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.CheckedMemberResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static java.util.stream.Collectors.toList;

@Log
@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long insertMeeting(AdminRequestDto dto) throws IllegalArgumentException {
        Optional<Meeting> target = meetingRepository.findByPasskey(dto.getPasskey());
        target.ifPresent(val -> {
            throw new IllegalArgumentException("이미 존재하는 출석키 입니다.");
        }); //출석키 중복확인

        if(!dto.getStartTime().isBefore(dto.getEndTime())){
            throw new IllegalArgumentException("종료시각이 시작시각보다 빠를 수 없습니다.");
        }

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
    public Long isExistKey(String passkey) throws IllegalArgumentException {
        Meeting meeting = meetingRepository.findByPasskey(passkey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));
        return meeting.getId();
    }

    @Transactional
    public Long insertAttendee(StudentSaveRequestDto dto) throws IllegalArgumentException, TimeoutException, NullPointerException {
        try {
            //passkey 확인
            Meeting meeting = meetingRepository.findByPasskey(dto.getPasskey())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));

            //회원 확인
            Member member = memberRepository.findByStudentId(dto.getStudentId()).
                    orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
            log.info("dto=" + dto.getGeneration() + ", " + dto.getName() + "/member=" + member.getGeneration() + ", " + member.getName());
            if (!((dto.getGeneration().equals(member.getGeneration())) && (dto.getName().equals(member.getName())))) {
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
        } catch (NullPointerException e) {
            throw new NullPointerException("입력하지 않은 항목이 있습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findMeetingsByStudentId(Long studentId) {
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return member.getMeetings().stream()
                .map(MeetingResponseDto::new)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<CheckedMemberResponseDto> findMembersByPasskey(String passkey) {
        return meetingRepository.findByPasskey(passkey).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."))
                .getMembers().stream().map(CheckedMemberResponseDto::new).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findAllPasskeys() {
        return meetingRepository.findAllByDesc()
                .stream()
                .map(MeetingResponseDto::new)
                .collect(toList());
    }

    @Transactional
    public Long deleteByPasskey(String passkey) throws IllegalArgumentException {
        Meeting meeting = meetingRepository.findByPasskey(passkey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출석키 입니다."));
        Long id = meeting.getId();

        meetingRepository.delete(meeting);
        return id;
    }

}
