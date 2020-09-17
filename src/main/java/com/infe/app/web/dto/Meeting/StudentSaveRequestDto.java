package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudentSaveRequestDto {
    private Long studentId;
    private String name;
    private Long groupNum;

    private String passkey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    public Member toMember() {
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .groupNum(groupNum)
                .build();
    }

    @Builder
    public StudentSaveRequestDto(Long studentId, String name, Long groupNum,
                                 String passkey, LocalDateTime dateTime) {
        this.studentId = studentId;
        this.name = name;
        this.groupNum = groupNum;
        this.passkey = passkey;
        this.dateTime = dateTime;
    }
}
