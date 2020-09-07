package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;


@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;

    @Test
    public void saveMeeting() {
        //given
        String inputValue = "FGA2X";
        Meeting meeting = Meeting.builder()
                .passkey(inputValue)
                .createdDateTime(LocalDateTime.of(2020, 11, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2020, 11, 11, 1, 0, 0))
                .build();

        //when
        meetingRepository.save(meeting);

        //then
        Meeting resMeeting = meetingRepository.findMeetingByPasskey(inputValue).get();
        assertThat(inputValue).isEqualTo(resMeeting.getPasskey());
    }

    @Test
    public void 참석자_올바른출석암호_인증성공한다() {
        //given
        String passkey = "SSD2K";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(110110L)
                .studentName("MOMO")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 0, 30, 0))
                .build();

        //then
        Long resId=0L;
        try {
            resId = meetingService.insertAttendee(dto);
        }catch(IllegalArgumentException e){
            log.info(">>>>>>>>인증실패..... 암호오류");
        }catch(TimeoutException te){
            log.info(">>>>>>>>인증실패..... 타임아웃");
        }

        //when
        assertThat(resId).isGreaterThan(0L);
    }

    @Test
    public void 참석자_틀린출석암호_인증실패한다() {
        //given
        String passkey = "SSD2KKKK";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(110110L)
                .studentName("MOMO")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 0, 30, 0))
                .build();

        //then, when
        try {
            Long resId = meetingService.insertAttendee(dto);
        }catch(IllegalArgumentException e){
            log.info(">>>>>>>>암호오류성공");
        }catch(TimeoutException te){
            log.info(">>>>>>>>암호오류실패..... 타임아웃");
        }
    }

    @Test
    public void 참석자_올바르지만_만료된출석암호_인증실패한다() {
        //given
        String passkey = "SSD2K";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(110110L)
                .studentName("MOMO")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 3, 00, 0))
                .build();

        //then, when
        try {
            Long resId = meetingService.insertAttendee(dto);
        }catch(IllegalArgumentException e){
            log.info(">>>>>>>>타임아웃 실패..... 암호오류");
        }catch(TimeoutException te){
            log.info(">>>>>>>>타임아웃 성공");
        }
    }

    @Test
    public void findAllMemberDesc() {
        //given
        List<String> names = Arrays.asList("park", "lee", "kim");
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 11, 0, 0, 0);

        //when
        List<Member> members = meetingRepository.findAllMemberDesc(dateTime);

        //then
        int i = 0;
        for (Member m : members) {
            assertThat(m.getName()).isEqualTo(names.get(i));
            log.info("[" + m.getId() + "]" + m.getStudentId() + ", " + m.getName());
            i++;
        }
    }

    @Test
    public void findAllMemberAndMeeting() {
        //when
        List<MemberMeetingResponseDto> dtos = meetingRepository.findAllMember();

        //then
        for (MemberMeetingResponseDto dto : dtos) {
            log.info(dto.toString());
        }
    }

    @Test
    public void deleteAllByDate() {
        //when
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 11, 0, 0, 0);
        meetingService.deleteAllByDate(dateTime);

        //then
        List<MemberMeetingResponseDto> dtos = meetingRepository.findAllMember();
        Meeting meeting = meetingRepository.findMeetingByCreatedDateTime(dateTime).orElse(null);
        assertThat(meeting).isEqualTo(null);
        for (MemberMeetingResponseDto dto : dtos) {
            assertThat(dto.getCreatedDateTime()).isNotEqualTo(dateTime);
        }
    }
}
