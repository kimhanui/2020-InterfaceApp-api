package com.infe.app.web;

import com.infe.app.service.CalendarService;
import com.infe.app.web.dto.CalendarRequestDto;
import com.infe.app.web.dto.CalendarResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar/**")
@RestController
public class CalendarApiController {
    private final CalendarService calendarService;

    @PostMapping
    public Long save(@RequestBody CalendarRequestDto calendarRequestDto) throws Exception{
        return calendarService.insert(calendarRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody CalendarRequestDto calendarRequestDto )throws Exception{
        return calendarService.update(id, calendarRequestDto);
    }

    @GetMapping("/{id}")
    public CalendarResponseDto find(@PathVariable Long id)throws Exception{
        return calendarService.find(id);
    }
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id)throws Exception {
        return calendarService.delete(id);
    }
}
