package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.meeting.MeetingRepository;
import com.infe.app.domain.member.Member;
import com.infe.app.web.dto.Meeting.*;
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
    private MemberService memberService;

    private final Double lat = 33.33;
    private final Double lon = 22.22;

    @Test
    public void passkey로_찾기() {
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
    public void Meeting_저장() {
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
    public void Meeting중복_예외발생() throws Exception {
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
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .lat(lat)
                .lon(lon)
                .build();

        //then
        Long resId = 0L;
        resId = meetingService.insertAttendee(dto);

        //when
        assertThat(resId).isGreaterThan(0L);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void 참석자_회원정보불일치_인증실패() throws IllegalArgumentException, TimeoutException {
//        //given
//        String passkey = "VJ5FG";
//        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
//                .studentId(100100L)
//                .name("WrongKim")
//                .generation(30L)
//                .passkey(passkey)
//                .dateTime(LocalDateTime.toDto(2020, 9, 9, 0, 30, 0))
//                .lat(lat)
//                .lon(lon)
//                .build();
//
//        //then, when
//        meetingService.insertAttendee(dto);
//    }


    @Test(expected = IllegalArgumentException.class)
    public void 참석자_틀린출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "SSD2KKKK";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .lat(lat)
                .lon(lon)
                .build();

        //then, when
        meetingService.insertAttendee(dto);
    }

    @Test(expected = TimeoutException.class)
    public void 참석자_만료된출석암호_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        String passkey = "VJ5FG";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 11, 11, 3, 00, 0))
                .lat(lat)
                .lon(lon)
                .build();

        //then, when
        meetingService.insertAttendee(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 참석자_위치인증오류_인증실패한다() throws IllegalArgumentException, TimeoutException {
        //given
        Double wrongLat = 55.55;
        String passkey = "VJ5FG";
        StudentSaveRequestDto dto = StudentSaveRequestDto.builder()
                .studentId(100100L)
                .name("kim")
                .generation(30L)
                .passkey(passkey)
                .dateTime(LocalDateTime.of(2020, 9, 9, 0, 30, 0))
                .lat(wrongLat)
                .lon(lon)
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
            targetG = m.getGeneration();
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
    public void 해당passkey의_Member조회() {
        //given
        String passkey = "VJ5FG";

        //when
        List<CheckedMemberResponseDto> list = meetingService.findMembersByPasskey(passkey);

        //then
        assertThat(list.stream().map(CheckedMemberResponseDto::getName).collect(toList()))
                .contains("yun", "lim", "oh");
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
        String passkey = "TMPKEY";
        AdminRequestDto adminRequestDto = AdminRequestDto.builder()
                .passkey(passkey)
                .lat(lat)
                .lon(lon)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1L))
                .build();
        meetingService.insertMeeting(adminRequestDto);

        //모임에 출석한 회원 생성
        Member member = memberService.find(1L).toEntity();
        StudentSaveRequestDto studentSaveRequestDto = StudentSaveRequestDto.builder()
                .studentId(member.getStudentId())
                .dateTime(LocalDateTime.now().plusMinutes(30L))
                .lat(lat)
                .lon(lon)
                .name("kim")
                .generation(30L)
                .passkey(passkey).build();
        meetingService.insertAttendee(studentSaveRequestDto);

        //when
        Long id = meetingService.deleteByPasskey(passkey);

        //then
        assertThat(member.getMeetings().stream().map(Meeting::getPasskey).collect(Collectors.toList())).doesNotContain(passkey);
    }


}
