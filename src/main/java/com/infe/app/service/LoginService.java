package com.infe.app.service;

import com.infe.app.domain.login.Login;
import com.infe.app.domain.login.LoginRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@RequiredArgsConstructor
@Service
public class LoginService {
    private final LoginRepository loginRepository;

    @Transactional(readOnly = true)
    public boolean login(LoginDto loginDto) throws IllegalArgumentException, NullPointerException {
        Login.Role role = Login.Role.valueOfLabel(loginDto.getRole());
        String pw = loginDto.getPw();

        Login login = loginRepository.findByRole(role).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("권한 종류")));
        String SAVEDPW  = login.getPw();

        return (pw.equals(SAVEDPW))? true: false;
    }

    @Transactional
    public String update(LoginDto loginDto) {
        Login login = loginRepository.findByRole(Login.Role.valueOfLabel(loginDto.getRole())).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("권한 종류")));
        login.updatePassword(loginDto.getPw());
        return "update success";
    }

    @Transactional(readOnly = true)
    public LoginDto find(Login.Role role) {
        Login login = loginRepository.findByRole(role).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("권한 종류")));
        return new LoginDto(login.getRole().getValue(), login.getPw());
    }
}
