package com.infe.app.service;

import com.infe.app.web.dto.CalendarRequestDto;
import com.infe.app.web.dto.CalendarResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarServiceTest {
    @Autowired
    private CalendarService calendarService;

    @Test
    public void 저장하기() {
        //given
        String title = "seminar";
        String content = "learning Spring Boot";
        CalendarRequestDto calendarRequestDto = CalendarRequestDto.builder()
                .date(LocalDate.now())
                .title(title)
                .content(content)
                .build();

        //when
        Long id = calendarService.insert(calendarRequestDto);

        //then
        CalendarResponseDto calendarResponseDto = calendarService.find(id);
        assertThat(calendarResponseDto.getTitle()).isEqualTo(title);
        assertThat(calendarResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    public void 수정하기() {
        //given
        String title = "seminar";
        String content = "learning Spring Boot";
        CalendarRequestDto calendarRequestDto = CalendarRequestDto.builder()
                .date(LocalDate.now())
                .title(title)
                .content(content)
                .build();

        //when
        calendarService.update(1L, calendarRequestDto);

        //then
        CalendarResponseDto calendarResponseDto = calendarService.find(1L);
        assertThat(calendarResponseDto.getTitle()).isEqualTo(title);
        assertThat(calendarResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    public void 삭제하기() {
        //given
        String title = "seminar";
        String content = "learning Spring Boot";
        CalendarRequestDto calendarRequestDto = CalendarRequestDto.builder()
                .date(LocalDate.now())
                .title(title)
                .content(content)
                .build();
        Long id = calendarService.insert(calendarRequestDto);

        //when
        Long cnt = calendarService.findAll().stream().count();
        calendarService.delete(id);

        //then
        Long afterCnt = calendarService.findAll().stream().count();
        assertThat(afterCnt).isEqualTo(cnt - 1);
    }
}
