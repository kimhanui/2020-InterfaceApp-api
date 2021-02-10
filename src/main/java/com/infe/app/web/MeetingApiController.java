package com.infe.app.web;

import com.infe.app.service.AttendanceService;
import com.infe.app.service.MeetingService;
import com.infe.app.web.dto.Meeting.AdminRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceRequestDto;
import com.infe.app.web.dto.Meeting.AttendanceResponseDto;
import com.infe.app.web.dto.Meeting.MeetingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Log
@RequestMapping("/api/v1/meet/**")
@RequiredArgsConstructor
@RestController
public class MeetingApiController {

    private final MeetingService meetingService;
    private final AttendanceService attendanceService;

    @PostMapping("/insert")
    public Long insertMeeting(@RequestBody AdminRequestDto dto) throws Exception {
        return meetingService.insertMeeting(dto);
    }

    @PostMapping("/userCheck")
    public ResponseEntity<String> attendanceChecking(@Valid @RequestBody AttendanceRequestDto dto) throws Exception {
        Long resId = attendanceService.attendanceChecking(dto);
        return new ResponseEntity<>(String.valueOf(resId), HttpStatus.OK);
    }

    @GetMapping("/findMeeting")
    public ResponseEntity<String> isMeetingExist(@RequestParam String passkey) throws Exception {
        Long res = meetingService.isExistKey(passkey);
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    @GetMapping("/list/studentId") //회원별
    public List<MeetingResponseDto> findMeetingsByStudentId(@RequestParam Long studentId) throws Exception {
        return attendanceService.findAttendanceByStudentId(studentId);
    }

    @GetMapping("/list/passkey") //암호별
    public List<AttendanceResponseDto> findMembersByPassKey(@RequestParam String passkey) throws Exception {
        return attendanceService.findParticipantsByPasskey(passkey);
    }

    @GetMapping("/passkeys") //관리자가 생성한 passkey 조회(createdDateTime Desc정렬)
    public List<MeetingResponseDto> findAllPasskeys() throws Exception {
        return meetingService.findAllPasskeys();
    }

    @DeleteMapping //passkey별
    public Long deletePasskey(@RequestBody Map<String,String> passkeyRequestData) throws Exception {
        return attendanceService.deleteByPasskey(passkeyRequestData.get("passkey"));
    }
}
