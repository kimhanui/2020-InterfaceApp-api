package com.infe.app.web;

import com.infe.app.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log
@RequiredArgsConstructor
@RequestMapping("/api/v1/token/**")
@RestController
public class FcmTokenApiController {
    private final FcmTokenService fcmTokenService;

    @PostMapping
    public Long save(@RequestBody Map<String, String> fcmTokenRequest) throws Exception {
        return fcmTokenService.insert(fcmTokenRequest.get("token"));
    }

    @GetMapping("/list")
    public List<Map<String, String>> findAll() throws Exception {
        return fcmTokenService.findAll();
    }

    @DeleteMapping
    public Long delete(@RequestBody Map<String, String> fcmTokenData) throws Exception {
        return fcmTokenService.delete(fcmTokenData.get("token"));
    }
}
