package com.infe.app.web;

import com.infe.app.service.MeetingService;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.MeetingRequestDto;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Log
@RequiredArgsConstructor
@RestController
public class MeetingApiController { //0000으로 초기화?

    private final MeetingService meetingService;

    @PostMapping("/api/v1/meet/insert")
    public Long insertMeeting(@RequestBody AdminRequestDto dto) {
        return meetingService.insertMeeting(dto);
    }

    @PostMapping("/api/v1/meet/findMeeting")
    public Long isMeetingExist(@RequestBody AdminRequestDto dto) {
        return meetingService.isMeetingExist(dto);
    }

    @PostMapping("/api/v1/meet/userCheck")
    public ResponseEntity<String> insertAttendee(@RequestBody StudentSaveRequestDto dto) {

        try {
            Long resId = meetingService.insertAttendee(dto);
            return new ResponseEntity<>(String.valueOf(resId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("잘못된 출석암호입니다..", HttpStatus.BAD_REQUEST);
        } catch (TimeoutException te) {
            return new ResponseEntity<>("만료된 출석암호입니다..", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/api/v1/meet/userList") //날짜별
    public List<MemberResponseDto> findAllMemberByDate( @RequestBody MeetingRequestDto dto) {
        return meetingService.findAllMemberDateDesc(dto);
    }

    @GetMapping("/api/v1/meet/userList")
    public List<MemberMeetingResponseDto> findAllMember() {
        return meetingService.findAllMember();
    }

    //삭제 모호 - 날짜별 출석한 학생 삭제
    @PostMapping("/api/v1/meet/deleteAll") //날짜별
    public ResponseEntity<String> deleteAllMember(@RequestBody MeetingRequestDto dto) {
        try{
            meetingService.deleteAllByDate(dto);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
