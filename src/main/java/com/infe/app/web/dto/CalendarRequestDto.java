package com.infe.app.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.calendar.Calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CalendarRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private String title;
    private String content;

    public Calendar toEntity(){
        return Calendar.builder()
                .date(date)
                .title(title)
                .content(content)
                .build();
    }

    @Builder
    public CalendarRequestDto(LocalDate date, String title, String content){
        this.date = date;
        this.title = title;
        this.content = content;
    }
}
