package com.infe.app.web.dto;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRequestDtoTest {

    @Test
    public void Member필드중_notnull이면서_비어있는것_찾아낸다(){
        //given
        MemberRequestDto dto = new MemberRequestDto();
        List<String> names = Arrays.asList("studentId","name","generation");

        //when
        List<String> list = dto.getBlank();

        //then
        assertThat(list).isEqualTo(names);
    }
}
