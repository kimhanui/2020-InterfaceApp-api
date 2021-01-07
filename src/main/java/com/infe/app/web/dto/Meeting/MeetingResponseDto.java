package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.meeting.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MeetingResponseDto {
    private String passkey;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public MeetingResponseDto(Meeting meeting) {
        this.passkey = meeting.getPasskey();
        this.startTime = meeting.getCreatedDateTime();
        this.endTime = meeting.getEndDateTime();
    }
}
