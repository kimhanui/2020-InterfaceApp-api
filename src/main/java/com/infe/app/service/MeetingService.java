package com.infe.app.service;

import com.infe.app.domain.attendee.Attendee;
import com.infe.app.domain.attendee.AttendeeRepository;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.constraints.Null;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Log
@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AttendeeRepository attendeeRepository;

    @Transactional
    public Long insertMeeting(AdminRequestDto dto) throws IllegalArgumentException {
        Optional<Meeting> target = meetingRepository.findByPasskey(dto.getPasskey());
        target.ifPresent(val -> {
            throw new IllegalArgumentException("이미 존재하는 출석키 입니다.");
        }); //출석키 중복확인

        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")));
        return meeting.getId();
    }

    @Transactional
    public Long attendanceChecking(AttendanceRequestDto dto) throws IllegalArgumentException, TimeoutException {

        //passkey 확인
        Meeting meeting = meetingRepository.findByPasskey(dto.getPasskey())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")));

        //출석 중복 확인
        Attendee attendee = attendeeRepository.findByStudentId(dto.getStudentId()).orElse(null);
        if (attendee == null) {
            boolean isUnique = attendeeRepository.findAll().stream()
                    .map(Attendee::getToken)
                    .noneMatch(t -> dto.getToken().equals(t));
            if (isUnique == true)
                attendee = attendeeRepository.save(dto.toEntity());
            else throw new IllegalArgumentException("이미 사용중인 토큰입니다.");
        }
        if (meeting.getAttendees().contains(attendee)) {
            throw new IllegalArgumentException("이미 출석되었습니다.");
        }

        //출석키 만료 확인
        LocalDateTime meetingStart = meeting.getCreatedDateTime();
        LocalDateTime meetingEnd = meeting.getEndDateTime();
        LocalDateTime inputTime = dto.getDateTime();
        if (!(inputTime.isAfter(meetingStart) && inputTime.isBefore(meetingEnd)))
            throw new TimeoutException("출석키가 만료되었습니다.");

        //위치 확인
        final double DIFF = 0.00005;
        if (!(((meeting.getLat() - DIFF < dto.getLat() && dto.getLat() < meeting.getLat() + DIFF)
                && (meeting.getLon() - DIFF < dto.getLon() && dto.getLon() < meeting.getLon() + DIFF)))) {
            throw new IllegalArgumentException("출석 위치가 다릅니다.");
        }

        // token 일치 확인 및 업데이트
        if (!attendee.getToken().equals(dto.getToken())) {
            boolean isUnique = attendeeRepository.findAll().stream()
                    .map(Attendee::getToken)
                    .noneMatch(t -> dto.getToken().equals(t));
            if (isUnique == true)
                attendee.updateToken(dto.getToken());
            else throw new IllegalArgumentException("이미 사용중인 토큰입니다.");
        }

        //출석 체크 처리
        attendee.addMeeting(meeting);
        return attendee.getId();

    }

    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findMeetingsByStudentId(Long studentId) {
        return attendeeRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("회원")))
                .getMeetings()
                .stream()
                .map(MeetingResponseDto::new)
                .sorted((m1, m2) -> m1.getStartTime().isBefore(m2.getStartTime()) ? 1 : 0)
                .collect(Collectors.toList());


    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> findMembersByPasskey(String passkey) {
        return meetingRepository.findByPasskey(passkey)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")))
                .getAttendees()
                .stream()
                .map(AttendanceResponseDto::new)
                .sorted((a1, a2) -> (int) (a1.getGeneration() - a2.getGeneration()))
                .sorted((a1, a2) -> (int) (a1.getStudentId() - a2.getStudentId()))
                .collect(toList());
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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")));
        Long id = meeting.getId();

        meeting.getAttendees().stream()
                .forEach(
                        attendee -> {
                            if (attendee.getMeetings().contains(meeting))
                                attendee.getMeetings().remove(meeting);
                        });
        meetingRepository.delete(meeting);
        return id;
    }

}
