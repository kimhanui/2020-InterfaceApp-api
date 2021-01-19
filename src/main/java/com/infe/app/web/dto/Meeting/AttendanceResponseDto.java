package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.attendee.Attendee;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceResponseDto {
    private Long studentId;
    private String name;
    private Long generation;

    public AttendanceResponseDto(Attendee attendee) {
        this.studentId = attendee.getStudentId();
        this.name = attendee.getName();
        this.generation = attendee.getGeneration();
    }
}