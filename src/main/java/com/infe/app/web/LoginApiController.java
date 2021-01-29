package com.infe.app.web;

import com.infe.app.domain.login.Login;
import com.infe.app.service.LoginService;
import com.infe.app.web.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log
@RequiredArgsConstructor
@RequestMapping("/api/v1/login/**")
@RestController
public class LoginApiController {
    private final LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginDto loginDto) {
        boolean result = loginService.login(loginDto);
        return (result == true) ? "login success" : "login failed";
    }

    @PostMapping("/update")
    public String update(@RequestBody LoginDto loginDto) {
        return loginService.update(loginDto);
    }

    @PostMapping("/find")
    public LoginDto find(@RequestBody Map<String, String> dto) {
        return loginService.find(Login.Role.valueOfLabel(dto.get("role")));
    }
}
