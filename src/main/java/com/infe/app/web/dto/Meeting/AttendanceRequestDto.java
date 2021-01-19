package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.attendee.Attendee;
import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AttendanceRequestDto {
    private Long studentId;
    private String name;
    private Long generation;
    private String passkey;
    private String token;
    private Double lat;
    private Double lon;
    private LocalDateTime dateTime = LocalDateTime.now();

    public Attendee toAttendee() {
        return Attendee.builder()
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .token(token)
                .build();
    }

    @Builder
    public AttendanceRequestDto(Long studentId, String name, Long generation,
                                String passkey, LocalDateTime dateTime, String token,  Double lat, Double lon) {
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.passkey = passkey;
        this.dateTime = dateTime;
        this.token = token;
        this.lat = lat;
        this.lon = lon;
    }
}
