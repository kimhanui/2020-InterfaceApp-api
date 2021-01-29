package com.infe.app.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.calendar.Calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CalendarResponseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private String title;
    private String content;

    public CalendarResponseDto(Calendar calendar){
        this.title = calendar.getTitle();
        this.date = calendar.getDate();
        this.content = calendar.getContent();
    }
}
