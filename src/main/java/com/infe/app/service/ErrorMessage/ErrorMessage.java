package com.infe.app.service.ErrorMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMessage {

    public static String NoExist(String target){
        return "존재하지 않는 "+target+" 입니다.";
    }

    public static String AlreadyExist(String target){
        return "이미 존재하는 "+target+" 입니다.";
    }
}
