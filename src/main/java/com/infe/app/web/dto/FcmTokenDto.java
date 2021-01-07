package com.infe.app.web.dto;

import com.infe.app.domain.FcmToken.FcmToken;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmTokenDto {
    String token;

    public FcmTokenDto(FcmToken fcmToken){
        this.token = fcmToken.getToken();
    }

    public FcmToken toEntity(){
        return new FcmToken(token);
    }
}
