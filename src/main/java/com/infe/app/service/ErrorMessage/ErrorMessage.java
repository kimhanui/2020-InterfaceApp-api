package com.infe.app.service.ErrorMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMessage {

    public static String NoExist(String target){
        return "존재하지 않는 "+target+" 입니다.";
    }

    public static String NoMatch(String target){
        return target+"가 일치하지 않습니다.";
    }
}
