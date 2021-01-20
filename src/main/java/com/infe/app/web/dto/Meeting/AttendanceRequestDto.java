package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.attendee.Attendee;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class AttendanceRequestDto {

    private Long studentId;

    @NotEmpty //null과 ""거부
    private String name;

    @NotNull //null만 거부. 숫자형은 ""검사를 못하므로 Notnull을 쓴다.
    private Long generation;

    @NotEmpty
    private String passkey;

    @NotEmpty
    private String token;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    private LocalDateTime dateTime = LocalDateTime.now();

    public Attendee toEntity() {
        return Attendee.builder()
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .token(token)
                .build();
    }

    @Builder
    public AttendanceRequestDto(Long studentId, String name, Long generation,
                                String passkey, LocalDateTime dateTime, String token, Double lat, Double lon) {
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
