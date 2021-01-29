package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.participant.Participant;
import com.infe.app.domain.meeting.Meeting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AttendeeMeetingResponseDto {
    //for Member
    private Long id;
    private Long studentId;
    private String name;
    private Long generation;

    //for Meeting
    private String passkey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;

    public AttendeeMeetingResponseDto(Participant att, Meeting mt){
        this.id = att.getId();
        this.studentId = att.getStudentId();
        this.name = att.getName();
        this.generation = att.getGeneration();
        this.passkey = mt.getPasskey();
        this.createdDateTime = mt.getCreatedDateTime();
    }

    @Override
    public String toString() {
        return "AttendeeMeetingResponseDto{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", name='" + name + '\'' +
                ", generation=" + generation +
                ", passkey='" + passkey + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
