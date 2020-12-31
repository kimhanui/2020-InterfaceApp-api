package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.MeetingRequestDto;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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

    @Test
    public void passkey로_찾기() {
        //given
        String passkey = "SSD2K";
        AdminRequestDto dto = AdminRequestDto.builder()
                .passkey(passkey)
                .startTime(LocalDateTime.of(2020, 11, 11, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 11, 11, 1, 0, 0))
                .build();

        //when
        Long res = -1L;
        res = meetingService.isExistKey(dto);

        //then
        assertThat(res).isGreaterThan(0L);
        log.info(res.toString());
    }

    @Test
    public void Meeting_저장() {
        //given
        String inputValue = "ZZZZZ";
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

    @Test(expected = IllegalArgumentException.class)
    public void Meeting중복_예외발생() throws Exception {
        //given

        String inputValue = "SSD2K";
        AdminRequestDto meetingDto = AdminRequestDto.builder()
                .passkey(inputValue)
                .startTime(LocalDateTime.of(2020, 11, 11, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 11, 11, 1, 0, 0))
                .build();

        //when, then
        meetingService.insertMeeting(meetingDto);
    }

    @Test
    public void 참석자_올바른출석암호_인증성공한다() throws TimeoutException {
        //given
        String passkey = "VJ5FG";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .build();

        //then
        Long resId = 0L;
        resId = meetingService.insertAttendee(dto);

        //when
        assertThat(resId).isGreaterThan(0L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 참석자_틀린출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "SSD2KKKK";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .build();

        //then, when
        meetingService.insertAttendee(dto);
    }

    @Test(expected = TimeoutException.class)
    public void 참석자_올바르지만_만료된출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "VJ5FG";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .groupNum(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 3, 00, 0))
                .build();

        //then, when
        meetingService.insertAttendee(dto);
    }

    @Test
    public void findAllMemberAsc() {
        //given
        List<String> names = Arrays.asList("park", "lee", "kim");
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 11, 0, 0, 0);

        //when
        List<Member> members = meetingRepository.findMembersByDate(dateTime);

        //then
        Long G = 0L, ID = 0L;
        Long targetG, targetID;
        for (Member m : members) {
            targetG = m.getGroupNum();
            targetID = m.getStudentId();

            //then
            assertThat(targetG).isGreaterThanOrEqualTo(G);
            if (targetG == G) {
                assertThat(targetID).isGreaterThan(ID);
            } else if (targetG > G) {
                G = targetG;
                ID = targetID;
            }
        }
    }

    @Test
    public void findAllMemberAndMeeting() {
        //when
        List<MemberMeetingResponseDto> dtos = meetingRepository.findAllMember();

        //then
        Long targetID, ID = 0L;
        LocalDateTime targetM, MEETCREATEDTIME = LocalDateTime.MIN;
        for (MemberMeetingResponseDto dto : dtos) {
            targetM = dto.getCreatedDateTime();
            targetID = dto.getStudentId();
            log.info(targetM + " " + targetID);

            //then
            assertThat(targetM).isAfterOrEqualTo(MEETCREATEDTIME);
            if (targetM == MEETCREATEDTIME) {
                assertThat(targetID).isGreaterThan(ID);
            } else if (targetM.isAfter(MEETCREATEDTIME)) {
                MEETCREATEDTIME = LocalDateTime.of(targetM.toLocalDate(), targetM.toLocalTime());
                ID = targetID;
            }
        }
    }

    @Test
    public void deleteMeetingsByDate() throws IllegalArgumentException {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 11, 0, 0, 0);
        List<Member> memberDtos = meetingService.findMembersByDate(dateTime).stream()
                .map(MemberResponseDto::toEntity).collect(Collectors.toList());
        MeetingRequestDto target = new MeetingRequestDto(dateTime);


        //when
        meetingService.deleteByDate(target);
//        Meeting meeting = meetingRepository.findMeetingsByCreatedDateTime(dateTime).orElse(null);

        //then
//        assertThat(meeting).isEqualTo(null); //meeting 삭제 확인
//        for (Member dto : memberDtos) { //각 member에서 삭제 확인
//            List<Meeting> meetings = dto.getMeetings();
//
//            log.info("확인 멤버: "+ dto.getName());
//            for( Meeting m:meetings){
//                log.info(m.getPasskey()+" "+m.getCreatedDateTime());
//            }
//            assertThat(dto.getCreatedDateTime()).isNotEqualTo(dateTime);
//        }
    }
}
