package com.infe.app.web;

import com.infe.app.service.MeetingService;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.CheckedMemberResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import com.infe.app.web.dto.Meeting.StudentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequestMapping("/api/v1/meet/**")
@RequiredArgsConstructor
@RestController
public class MeetingApiController { //0000으로 초기화?

    private final MeetingService meetingService;

    @PostMapping("/insert")
    public Long insertMeeting(@RequestBody AdminRequestDto dto) throws Exception {
        return meetingService.insertMeeting(dto);
    }

    @GetMapping("/findMeeting")
    public ResponseEntity<String> isMeetingExist(@RequestParam String passkey) throws Exception {
        Long res = meetingService.isExistKey(passkey);
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    @PostMapping("/userCheck")
    public ResponseEntity<String> insertAttendee(@RequestBody StudentSaveRequestDto dto) throws Exception {
        Long resId = meetingService.insertAttendee(dto);
        return new ResponseEntity<>(String.valueOf(resId), HttpStatus.OK);
    }

    @GetMapping("/list") //회원별
    public List<MeetingResponseDto> findMeetingsByStudentId(@RequestParam Long studentId) throws Exception {
        return meetingService.findMeetingsByStudentId(studentId);
    }

    @PostMapping("/list") //암호별
    public List<CheckedMemberResponseDto> findMembersByPassKey(@RequestParam String passkey) throws Exception {
        return meetingService.findMembersByPasskey(passkey);
    }

    @GetMapping("/passkeys") //관리자가 생성한 passkey 조회(createdDateTime Desc정렬)
    public List<MeetingResponseDto> findAllPasskeys() throws Exception {
        return meetingService.findAllPasskeys();
    }

    @DeleteMapping //passkey별
    public Long deletePasskey(@RequestParam String passkey) throws Exception {
        return meetingService.deleteByPasskey(passkey);
    }
}
