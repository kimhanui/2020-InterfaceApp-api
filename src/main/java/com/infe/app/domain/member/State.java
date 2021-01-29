package com.infe.app.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * enum: AA("key1","key2","key3")으로 지정하고
 * 필드 + 생성자 생성하면 이름 순서대로 매칭되는 것 같음
 **/
@Getter
@NoArgsConstructor
public enum State {
    ATTENDING("재학"),
    MILITARY("군휴학"),
    REST("휴학"),
    GRADUATED("졸업");
    private String value;

    State(String value) {
        this.value = value;
    }

    public static State valueOfLabel(String value) {
        return Arrays.stream(State.values())
                .filter(v -> v.getValue().equals(value) )
                .findAny()
                .orElse(null);
    }
}
