package com.infe.app.web.dto.Meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AdminRequestDto {
    private String passKey;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
