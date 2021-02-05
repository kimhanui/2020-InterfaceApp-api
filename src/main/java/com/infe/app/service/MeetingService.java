package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Log
@RequiredArgsConstructor
@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    @Transactional
    public Long insertMeeting(AdminRequestDto dto) throws IllegalArgumentException {
        Optional<Meeting> target = meetingRepository.findByPasskey(dto.getPasskey());
        target.ifPresent(val -> {
            throw new IllegalArgumentException("이미 존재하는 출석키 입니다.");
        }); //출석키 중복확인

        if (dto.getStartTime()!=null && dto.getStartTime().isBefore(LocalDateTime.now().minusMinutes(1L))) {
            throw new IllegalArgumentException("현재보다 이른시각에 모임생성할 수 없습니다.");
        }
        return meetingRepository.save(dto.toEntity()).getId();
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
                .sorted((m1, m2) -> m1.getCreatedDateTime().isAfter(m2.getCreatedDateTime()) ? 1 : 0) //최신순
                .map(MeetingResponseDto::new)
                .collect(toList());
    }
}
