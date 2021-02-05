package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.meeting.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class AdminRequestDto {

    private String passkey;
    private Double lat;
    private Double lon;
    /**개선사항: 입력 없으면.now()로**/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Meeting toEntity(){
        return Meeting.builder()
                .passkey(this.getPasskey())
                .lat(this.getLat())
                .lon(this.getLon())
                .createdDateTime((this.getStartTime()==null)? LocalDateTime.now(): this.getStartTime())
                .endDateTime(this.getStartTime().plusMinutes(30L))
                .build();
    }
}
