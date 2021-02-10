package com.infe.app.service;

import com.infe.app.domain.attandance.Attendance;
import com.infe.app.domain.attandance.AttendanceRepository;
import com.infe.app.domain.participant.Participant;
import com.infe.app.web.dto.Meeting.AttendanceRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceServiceTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

    private final Double lat = 33.33;
    private final Double lon = 22.22;

    @Test
    public void 참석자_올바른출석암호_인증성공한다() throws TimeoutException {
        //given
        String passkey = "VJ5FG";
        AttendanceRequestDto dto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .token("TOKEN1")
                .lat(lat)
                .lon(lon)
                .build();

        //then
        Long resId = attendanceService.attendanceChecking(dto);

        //when
        assertThat(resId).isGreaterThan(0L);
    }


    @Test(expected = IllegalArgumentException.class)
    public void 참석자_틀린출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "SSD2KKKK";
        AttendanceRequestDto dto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .token("TOKEN1")
                .lat(lat)
                .lon(lon)
                .build();

        //then, when
        attendanceService.attendanceChecking(dto);
    }

    @Test(expected = TimeoutException.class)
    public void 참석자_만료된출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "VJ5FG";
        AttendanceRequestDto dto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 3, 00, 0))
                .token("TOKEN1")
                .lat(lat)
                .lon(lon)
                .build();

        //then, when
        attendanceService.attendanceChecking(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 참석자_위치인증오류_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        Double wrongLat = 55.55;
        String passkey = "VJ5FG";
        AttendanceRequestDto dto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .token("TOKEN1")
                .lat(wrongLat)
                .lon(lon)
                .build();

        //then, when
        attendanceService.attendanceChecking(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 참석자_본인토큰이_아닌_토큰중복으로_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        Double wrongLat = 55.55;
        String passkey = "VJ5FG";
        AttendanceRequestDto dto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .token("TOKEN2")
                .lat(wrongLat)
                .lon(lon)
                .build();

        //then, when
        attendanceService.attendanceChecking(dto);
    }
    @Test
    public void passkey로_attendance_조회하기() {
        //given
        String passkey="SSD2K";

        //when
        List<AttendanceResponseDto> attendanceList = attendanceService.findParticipantsByPasskey(passkey);

        //then
        AttendanceResponseDto before =  attendanceList.get(0);
        for (AttendanceResponseDto dto:attendanceList) {
            assertThat(before.getStudentId()).isLessThanOrEqualTo(dto.getStudentId());
            if(before.getStudentId() < dto.getStudentId()) {
                continue;
            }
            else if(before.getStudentId() == dto.getStudentId()){
                assertThat(before.getGeneration()).isLessThanOrEqualTo(dto.getGeneration());
                if(before.getGeneration() <= dto.getGeneration()){
                    continue;
                }
                throw new IllegalReceiveException("generation sorting이 적절하지 않습니다.");
            }
            before = dto;
        }
    }

    @Test
    public void 해당passkey의_Member조회() {
        //given
        String passkey = "VJ5FG";

        //when
        List<AttendanceResponseDto> list = attendanceService.findParticipantsByPasskey(passkey);

        //then
        assertThat(list.stream().map(AttendanceResponseDto::getName).collect(toList()))
                .contains("yun", "lim", "oh");
    }
    @Test
    public void studentId로_Meeting조회() {
        //given
        Long studentId = 100100L;

        //when
        List<MeetingResponseDto> list = attendanceService.findAttendanceByStudentId(studentId);

        //then
        assertThat(list.stream().map(MeetingResponseDto::getPasskey).collect(toList()))
                .contains("SSD2K");
    }

    @Test
    public void meeting_id로_attendance_삭제하면_참석자에게서도_조회불가() {
        //given
        Long id = 1L;
        List<Attendance> attendance_before = attendanceRepository.findAllByMeetingId(id);
        List<Long> participantIds = attendance_before.stream()
                .map(Attendance::getParticipant)
                .map(Participant::getId)
                .collect(Collectors.toList());

        //when
        attendanceRepository.deleteAllByMeetingId(id);

        //then
        participantIds.stream().forEach(
                i -> {
                    attendanceRepository.findAllByParticipantId(i).stream().forEach(
                            a -> {
                                assertThat(a.getId()).isNotEqualTo(1L);
                            }
                    );
                });
    }

}
