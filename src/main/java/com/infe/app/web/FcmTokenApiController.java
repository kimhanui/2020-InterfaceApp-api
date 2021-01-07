package com.infe.app.web;

import com.infe.app.service.FcmTokenService;
import com.infe.app.web.dto.FcmTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RequestMapping("/api/v1/token/**")
@RestController
public class FcmTokenApiController {
    private final FcmTokenService fcmTokenService;

    @PostMapping
    public Long save(@RequestBody FcmTokenDto fcmTokenDto) throws Exception {
        return fcmTokenService.insert(fcmTokenDto);
    }

    @GetMapping("/list")
    public List<FcmTokenDto> findAll() throws Exception {
        return fcmTokenService.findAll();
    }

    @DeleteMapping
    public Long delete(@RequestBody FcmTokenDto fcmTokenDto) throws Exception {
        return fcmTokenService.delete(fcmTokenDto);
    }
}
