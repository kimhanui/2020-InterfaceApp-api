package com.infe.app.service;

import com.infe.app.domain.attandance.Attendance;
import com.infe.app.domain.attandance.AttendanceRepository;
import com.infe.app.domain.participant.Participant;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceServiceTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    public void meeting_id로_attendance_조회하기() {
        //given
        Long id = 1L;
        List<Attendance> attendance_before = attendanceRepository.findAllByMeeting_Id(id);
        log.info("before:" + attendance_before.stream()
                .map(Attendance::getParticipant)
                .map(Participant::getStudentId)
                .collect(Collectors.toList())
                .toString());
    }

    @Test
    public void meeting_id로_attendance_삭제하면_참석자에게서도_조회불가() {
        //given
        Long id = 1L;
        List<Attendance> attendance_before = attendanceRepository.findAllByMeeting_Id(id);
        List<Long> participantIds = attendance_before.stream()
                .map(Attendance::getParticipant)
                .map(Participant::getId)
                .collect(Collectors.toList());

        //when
        attendanceRepository.deleteAllByMeeting_Id(id);

        //then
        participantIds.stream().forEach(
                i -> {
                    attendanceRepository.findAllByParticipant_Id(i).stream().forEach(
                            a -> {
                                assertThat(a.getId()).isNotEqualTo(1L);
                            }
                    );
                });

    }
}
