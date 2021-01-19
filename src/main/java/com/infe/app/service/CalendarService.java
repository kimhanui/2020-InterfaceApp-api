package com.infe.app.service;

import com.infe.app.domain.calendar.Calendar;
import com.infe.app.domain.calendar.CalendarRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.CalendarRequestDto;
import com.infe.app.web.dto.CalendarResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    @Transactional
    public Long insert(CalendarRequestDto calendarRequestDto) {
        return calendarRepository.save(calendarRequestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, CalendarRequestDto calendarRequestDto) throws IllegalArgumentException{
        Calendar calendar = calendarRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 일정입니다."));
        log.info(calendar.toString());
        calendar.update(calendar);
        return calendar.getId();
    }
    @Transactional(readOnly = true)
    public CalendarResponseDto find(Long id) throws IllegalArgumentException{
        Calendar calendar = calendarRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 일정입니다."));
        log.info(calendar.toString());
        return new CalendarResponseDto(calendar);
    }
    @Transactional
    public Long delete(Long id) throws IllegalArgumentException{
        try {
            calendarRepository.deleteById(id);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException(ErrorMessage.NoExist("일정"));
        }

        return id;
    }
}
