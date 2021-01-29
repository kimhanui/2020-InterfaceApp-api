package com.infe.app.web.dto;

import com.infe.app.domain.login.Login;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String role;
    private String pw;

    public Login toEntity(){
        return Login.builder()
                .role(Login.Role.valueOfLabel(role))
                .pw(pw)
                .build();
    }
}
