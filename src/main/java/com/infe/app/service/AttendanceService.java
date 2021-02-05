package com.infe.app.service;

import com.infe.app.domain.attandance.Attendance;
import com.infe.app.domain.attandance.AttendanceRepository;
import com.infe.app.domain.participant.ParticipantRepository;
import com.infe.app.domain.participant.Participant;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.Meeting.AttendanceRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public Long attendanceChecking(AttendanceRequestDto dto) throws IllegalArgumentException, TimeoutException {

        //passkey 확인
        Meeting meeting = meetingRepository.findByPasskey(dto.getPasskey())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")));

        //토큰 유일성 확인(대리출석 방지)
        Participant participant = participantRepository.findByStudentId(dto.getStudentId()).orElse(null);
        if (participant == null) { //만료된 토큰이라 없는지 확인
            boolean isUnique = participantRepository.findAll().stream()
                    .map(Participant::getToken)
                    .noneMatch(t -> dto.getToken().equals(t));
            if (isUnique) //유일한 토큰이면 새로 저장(아직 출석아님)
                participant = participantRepository.save(dto.toEntity());
            else throw new IllegalArgumentException("이미 사용중인 토큰입니다.");
        }
        //중복 출석 방지
        List<Participant> participantList = meeting.getAttendances()
                .stream()
                .map(Attendance::getParticipant)
                .collect(Collectors.toList());
        if(participantList.contains(participant))
            throw new IllegalArgumentException("이미 출석되었습니다.");


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

        //출석 체크 처리
        Attendance attendance = Attendance.builder()
                .participant(participant)
                .meeting(meeting).build();
        return attendanceRepository.save(attendance).getId();
    }

    //출석시간 추가
    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findAttendanceByStudentId(Long studentId) {
        return participantRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("회원")))
                .getAttendances()
                .stream()
                .map(Attendance::getMeeting)
                .map(MeetingResponseDto::new)
                .sorted((m1, m2) -> m1.getStartTime().isBefore(m2.getStartTime()) ? 1 : 0)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> findParticipantsByPasskey(String passkey) {
        return meetingRepository.findByPasskey(passkey)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")))
                .getAttendances()
                .stream()
                .map(attendance -> new AttendanceResponseDto(attendance.getParticipant(), attendance.getAttendanceDateTime()))
                .sorted((a1, a2) -> (int) (a1.getGeneration() - a2.getGeneration()))
                .sorted((a1, a2) -> (int) (a1.getStudentId() - a2.getStudentId()))
                .collect(toList());
    }

    @Transactional
    public Long deleteByPasskey(String passkey) throws IllegalArgumentException, Exception {
        Meeting meeting = meetingRepository.findByPasskey(passkey)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("출석키")));
        Long id = meeting.getId();

        attendanceRepository.deleteAllByMeetingId(meeting.getId());
        meetingRepository.delete(meeting);
        return id;
    }
}
