package com.infe.app.domain.member;

import com.infe.app.converter.BooleanToYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;

@Log
@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class ManageStatus {
    @Convert(converter = BooleanToYNConverter.class)
    private boolean firstDues; //회비 납부 여부
    @Convert(converter = BooleanToYNConverter.class)
    private boolean secondDues;
    @Convert(converter = BooleanToYNConverter.class)
    private boolean openingMeeting; //개총 참석 여부
    @Convert(converter = BooleanToYNConverter.class)
    private boolean finalMeeting; //종총 참석 여부

    public ManageStatus(Map<String, String> data) {//entity
        if (data == null) {
            new ManageStatus(); // default false
        } else {
            this.firstDues = mapToBoolean(data.get("firstDues"));
            this.secondDues = mapToBoolean(data.get("secondDues"));
            this.openingMeeting = mapToBoolean(data.get("openingMeeting"));
            this.finalMeeting = mapToBoolean(data.get("finalMeeting"));
        }
    }

    public Map<String, String> toDto() { //dto
        Map<String, String> map = new HashMap<>();
        map.put("firstDues", mapToOx(firstDues));
        map.put("secondDues", mapToOx(secondDues));
        map.put("openingMeeting", mapToOx(openingMeeting));
        map.put("finalMeeting", mapToOx(finalMeeting));
        return map;
    }

    private String mapToOx(boolean b) {
        return (b == true) ? "O" : "X";
    }

    private boolean mapToBoolean(String s) {
        return s.equals("O");
    }
}
