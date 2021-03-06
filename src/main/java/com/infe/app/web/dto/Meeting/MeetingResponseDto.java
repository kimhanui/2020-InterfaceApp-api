package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.meeting.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MeetingResponseDto {
    private String passkey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    public MeetingResponseDto(Meeting meeting) {
        this.passkey = meeting.getPasskey();
        this.startTime = meeting.getCreatedDateTime();
        this.endTime = meeting.getEndDateTime();
    }
}
