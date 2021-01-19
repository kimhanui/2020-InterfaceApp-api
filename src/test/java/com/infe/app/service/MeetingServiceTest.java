package com.infe.app.service;

import com.infe.app.domain.attendee.Attendee;
import com.infe.app.domain.attendee.AttendeeRepository;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.web.dto.Meeting.*;
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

@Transactional //매 @Test마다 롤백시키기위함
@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private AttendeeRepository attendeeRepository;

    private final Double lat = 33.33;
    private final Double lon = 22.22;

    @Test
    public void passkey_존재한다() {
        //given
        String passkey = "SSD2K";

        //when
        Long res = -1L;
        res = meetingService.isExistKey(passkey);

        //then
        assertThat(res).isGreaterThan(0L);
        log.info(res.toString());
    }

    @Test
    public void Meeting_생성_성공() {
        //given
        String inputValue = "ZZZZZ";
        Meeting meeting = Meeting.builder()
                .passkey(inputValue)
                .createdDateTime(LocalDateTime.of(2020, 11, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2020, 11, 11, 1, 0, 0))
                .lat(lat)
                .lon(lon)
                .build();

        //when
        meetingRepository.save(meeting);

        //then
        Meeting resMeeting = meetingRepository.findByPasskey(inputValue).get();
        assertThat(inputValue).isEqualTo(resMeeting.getPasskey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void 중복Meeting_생성_예외발생() throws Exception {
        //given

        String inputValue = "SSD2K";
        AdminRequestDto meetingDto = AdminRequestDto.builder()
                .passkey(inputValue)
                .startTime(LocalDateTime.of(2020, 11, 11, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 11, 11, 1, 0, 0))
                .lat(lat)
                .lon(lon)
                .build();

        //when, then
        meetingService.insertMeeting(meetingDto);
    }

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
        Long resId = meetingService.attendanceChecking(dto);

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
        meetingService.attendanceChecking(dto);
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
        meetingService.attendanceChecking(dto);
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
        meetingService.attendanceChecking(dto);
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
        meetingService.attendanceChecking(dto);
    }

    @Test
    public void 해당studentId의_Meeting조회() {
        //given
        Long studentId = 100100L;

        //when
        List<MeetingResponseDto> list = meetingService.findMeetingsByStudentId(studentId);

        //then
        assertThat(list.stream().map(MeetingResponseDto::getPasskey).collect(toList()))
                .contains("SSD2K");
    }

    @Test
    public void 해당passkey의_Member조회() {
        //given
        String passkey = "VJ5FG";

        //when
        List<AttendanceResponseDto> list = meetingService.findMembersByPasskey(passkey);

        //then
        assertThat(list.stream().map(AttendanceResponseDto::getName).collect(toList()))
                .contains("yun", "lim", "oh");
    }

    @Test
    public void 모든_passkey조회_startTime_Desc정렬() {
        //given,when
        List<MeetingResponseDto> list = meetingService.findAllPasskeys();

        //then
        LocalDateTime date = list.get(0).getStartTime();
        for (MeetingResponseDto dto : list) {
            LocalDateTime startTime = dto.getStartTime();
            log.info(startTime.toString());
            assertThat(startTime).isBeforeOrEqualTo(date);
            date = startTime;

        }
    }

    @Test
    public void 해당passkey_모임삭제() throws TimeoutException {
        //given
        //모임 생성
        String targetPasskey = "TARGET";
        AdminRequestDto adminRequestDto = AdminRequestDto.builder()
                .passkey(targetPasskey)
                .lat(lat)
                .lon(lon)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1L))
                .build();
        meetingService.insertMeeting(adminRequestDto);

        //모임에 출석한 회원 생성
        AttendanceRequestDto attendanceRequestDto = AttendanceRequestDto.builder()
                .studentId(100100L)
                .dateTime(LocalDateTime.now().plusMinutes(30L))
                .token("TOKEN1")
                .lat(lat)
                .lon(lon)
                .name("kim")
                .generation(30L)
                .passkey(targetPasskey)
                .build();
        meetingService.attendanceChecking(attendanceRequestDto);

        //when
        meetingService.deleteByPasskey(targetPasskey);
        Attendee attendee = attendeeRepository.findByStudentId(100100L).get();

        //then
        assertThat(attendee.getMeetings().stream()
                .map(Meeting::getPasskey)
                .collect(Collectors.toList())).doesNotContain(targetPasskey);
    }
}
