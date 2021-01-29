package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.participant.Participant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AttendanceResponseDto {
    private Long studentId;
    private String name;
    private Long generation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;
    public AttendanceResponseDto(Participant participant, LocalDateTime attendanceTime) {
        this.studentId = participant.getStudentId();
        this.name = participant.getName();
        this.generation = participant.getGeneration();
        this.dateTime = attendanceTime;
    }
}