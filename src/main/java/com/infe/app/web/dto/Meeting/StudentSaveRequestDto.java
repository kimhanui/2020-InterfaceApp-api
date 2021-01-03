package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class StudentSaveRequestDto {
    private Long studentId;
    private String name;
    private Long generation;
    private String passkey;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    public Member toMember() {
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .build();
    }

    @Builder
    public StudentSaveRequestDto(Long studentId, String name, Long generation,
                                 String passkey, LocalDateTime dateTime) {
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.passkey = passkey;
        this.dateTime = dateTime;
    }
}
