package com.infe.app.service;

import com.infe.app.domain.attandance.AttendanceRepository;
import com.infe.app.domain.participant.ParticipantRepository;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Log
@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AttendanceRepository attendanceRepository;
    private final ParticipantRepository participantRepository;

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


    @Transactional(readOnly = true)
    public List<MeetingResponseDto> findAllPasskeys() {
        return meetingRepository.findAllByDesc()
                .stream()
                .map(MeetingResponseDto::new)
                .collect(toList());
    }



}
