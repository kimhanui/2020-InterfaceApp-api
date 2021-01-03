package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberMeetingResponseDto {
    private Long id;
    private Long studentId;
    private String name;
    private Long groupNum;
    private String passkey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;

    public MemberMeetingResponseDto(Member m, Meeting mt){
        this.id = m.getId();
        this.studentId = m.getStudentId();
        this.name = m.getName();
        this.groupNum = m.getGeneration();
        this.passkey = mt.getPasskey();
        this.createdDateTime = mt.getCreatedDateTime();
    }

    @Override
    public String toString() {
        return "MemberMeetingResponseDto{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", name='" + name + '\'' +
                ", groupNum=" + groupNum +
                ", passkey='" + passkey + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
