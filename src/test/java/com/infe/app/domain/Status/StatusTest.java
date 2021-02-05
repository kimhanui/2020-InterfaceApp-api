package com.infe.app.domain.Status;

import com.infe.app.domain.member.State;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusTest {

    @Test
    public void State_valueOfLabel에서_해당Enum반환된다(){
        //given
        State[] states = State.values();
        List<String> values = Arrays.stream(states)
                            .map(State::getValue)
                            .collect(Collectors.toList());
        //when, then
        values.stream()
                .forEach(
                        value -> assertThat(State.valueOfLabel(value)).isEqualTo(states[values.indexOf(value)])
                );
    }
}
