package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudentSaveRequestDto {
    private Long studentId;
    private String studentName;
    private Long groupNum;

    private String passkey;
    private LocalDateTime dateTime;

    public Member toMember() {
        return Member.builder()
                .studentId(studentId)
                .name(studentName)
                .groupNum(groupNum)
                .build();
    }

    @Builder
    public StudentSaveRequestDto(Long studentId, String studentName, Long groupNum,
                                 String passkey, LocalDateTime dateTime) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.groupNum = groupNum;
        this.passkey = passkey;
        this.dateTime = dateTime;
    }
}
