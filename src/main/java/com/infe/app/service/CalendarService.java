package com.infe.app.service;

import com.infe.app.domain.calendar.Calendar;
import com.infe.app.domain.calendar.CalendarRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.CalendarRequestDto;
import com.infe.app.web.dto.CalendarResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    @Transactional
    public Long insert(CalendarRequestDto calendarRequestDto) throws IllegalArgumentException{
        Optional<Calendar> calendar = calendarRepository.findByDate(calendarRequestDto.getDate());//.orElseThrow(()->new IllegalArgumentException(ErrorMessage.NoExist("일정")));
        calendar.ifPresent(v->{throw new IllegalArgumentException(ErrorMessage.AlreadyExist("일정"));});
        return calendarRepository.save(calendarRequestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, CalendarRequestDto calendarRequestDto) throws IllegalArgumentException{
        Calendar calendar = calendarRepository.findById(id).orElseThrow(()->new IllegalArgumentException(ErrorMessage.NoExist("일정")));
        log.info(calendar.toString());
        calendar.update(calendarRequestDto.toEntity());
        return calendar.getId();
    }
    @Transactional(readOnly = true)
    public CalendarResponseDto find(Long id) throws IllegalArgumentException{
        Calendar calendar = calendarRepository.findById(id).orElseThrow(()->new IllegalArgumentException(ErrorMessage.NoExist("일정")));
        log.info(calendar.toString());
        return new CalendarResponseDto(calendar);
    }

    @Transactional(readOnly = true)
    public List<CalendarResponseDto> findAll(){
        return calendarRepository.findAll()
                .stream()
                .map(CalendarResponseDto::new)
                .sorted((c1, c2) -> c1.getDate().isBefore(c2.getDate())?1:0)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long delete(Long id) throws IllegalArgumentException{
        try {
            calendarRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new IllegalArgumentException(ErrorMessage.NoExist("일정"));
        }
        return id;
    }
}
