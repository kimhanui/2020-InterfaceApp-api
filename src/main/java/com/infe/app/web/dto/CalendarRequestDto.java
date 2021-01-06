package com.infe.app.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infe.app.domain.Calendar.Calendar;
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
}
