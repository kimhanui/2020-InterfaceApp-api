package com.infe.app.web.dto.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AdminRequestDto {

    private String passkey;
    private Double lat;
    private Double lon;
    /**개선사항: 입력 없으면.now()로**/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    @Builder
    public AdminRequestDto(String passkey,Double lat, Double lon, LocalDateTime startTime, LocalDateTime endTime){
        this.passkey = passkey;
        this.lat = lat;
        this.lon = lon;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
