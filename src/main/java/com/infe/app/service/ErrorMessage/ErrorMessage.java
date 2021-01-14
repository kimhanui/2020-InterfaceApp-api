package com.infe.app.service.ErrorMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMessage {
    private String message;

    public static String NoExist(String target){
        return "존재하지 않는 "+target+" 입니다.";
    }
}
