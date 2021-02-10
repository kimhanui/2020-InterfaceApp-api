package com.infe.app.config;

import com.infe.app.domain.attandance.AttendanceRepository;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.participant.Participant;
import com.infe.app.domain.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. 출석체크 Attendee(한학기)
 * 2. 모임 Meeting(한학기)
 * 3. */
@Log
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final MeetingRepository meetingRepository;
    private final AttendanceRepository attendanceRepository;
    private final ParticipantRepository participantRepository;

    /**초, 분, 시, 일, 월, 요일**/
    @Transactional
    @Scheduled(cron = "0 0 0 * * SUN")
    public void meetingDeleteAllCronJob(){
        List<Long> ids =  meetingRepository.findAllByDesc()
                .stream()
                .filter(m -> m.getCreatedDateTime().isBefore(LocalDateTime.now()))
                .map(Meeting::getId)
                .collect(Collectors.toList());
        attendanceRepository.deleteAllByMeetingIdList(ids);
        meetingRepository.deleteAllById(ids);
        log.info("Cron Job Success: delete meeting every SUNDAY");
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 7,12 SUN")
    public void participantDeleteAllCronJob(){
        List<Long> ids =  participantRepository.findAll()
                .stream()
                .filter(m -> m.getCreatedDateTime().isBefore(LocalDateTime.now()))
                .map(Participant::getId)
                .collect(Collectors.toList());
        attendanceRepository.deleteAllByMeetingIdList(ids);
        participantRepository.deleteAllById(ids);
        log.info("Cron Job Success: delete participant every SEMESTER");
    }
}
