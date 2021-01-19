package com.infe.app.domain.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@NoArgsConstructor
@Getter
@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String pw;

    @Builder
    public Login(Role role, String pw){
        this.role = role;
        this.pw = pw;
    }

    public void updatePassword(String password){
        this.pw = password;
    }

    @Getter
    public enum Role {
        ADMIN("관리자"),
        USER("회원");

        private String value;

        Role(String value) {
            this.value = value;
        }

        public static Role valueOfLabel(String value) {
            return Arrays.stream(Role.values())
                    .filter(v -> v.getValue().equals(value))
                    .findAny()
                    .orElse(null);
        }
    }
}
