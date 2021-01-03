package com.infe.app.domain.member;

import com.infe.app.converter.BooleanToYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Embeddable
public class ManageStatus {

    @Setter
    @Enumerated(EnumType.STRING)
    private State attendingStatus = State.ATTENDING; //재학(기본), 휴학, 군휴학

    @Convert(converter = BooleanToYNConverter.class)
    private boolean firstDues = false; //회비 납부 여부
    @Convert(converter = BooleanToYNConverter.class)
    private boolean secondDues= false;
    @Convert(converter = BooleanToYNConverter.class)
    private boolean openingMeeting= false; //개총 참석 여부
    @Convert(converter = BooleanToYNConverter.class)
    private boolean finalMeeting= false; //종총 참석 여부

    /**
     * enum: AA("key1","key2","key3")으로 지정하고
     * 필드 + 생성자 생성하면 이름 순서대로 매칭되는 것 같음
    **/
    @Getter
    public enum State {
        ATTENDING("재학"),
        MILITARY("군휴학"),
        REST("휴학"),
        FREE("졸업");
        private String value;

        State(String value){
            this.value = value;
        }
    }
}
