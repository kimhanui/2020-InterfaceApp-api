package com.infe.app.domain.posts;

import com.infe.app.domain.login.Login;
import com.infe.app.domain.login.LoginRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginRepositoryTest {
    @Autowired
    LoginRepository loginRepository;

    @Test
    public void findByRole() {
        //given
        Login.Role role = Login.Role.ADMIN;
        Optional<Login> login = loginRepository.findByRole(role);

        //when, then
        assertThat(login.get().getPw()).isEqualTo("interfaceAdmin");
    }
}
