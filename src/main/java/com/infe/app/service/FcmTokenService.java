package com.infe.app.service;

import com.infe.app.domain.FcmToken.FcmToken;
import com.infe.app.domain.FcmToken.FcmTokenRespository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class FcmTokenService {
    private final FcmTokenRespository fcmTokenRespository;

    @Transactional
    public Long insert(String token) throws Exception {
        return fcmTokenRespository.save(new FcmToken(token)).getId();
    }

//    @Transactional
//    public Long update(String token){
//        FcmToken fcmToken = fcmTokenRespository.findByToken(token).orElseThrow(()->new IllegalArgumentException("존재하지 않는 토큰입니다."));
//        fcmToken.
//    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findAll() throws Exception {
        return fcmTokenRespository.findAll()
                .stream()
                .map(fcmToken -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("token", fcmToken.getToken());
                    return map;
                })
                .collect(toList());
    }

    @Transactional
    public Long delete(String token) throws Exception {
        try {
            fcmTokenRespository.deleteByToken(token);
            return 1L;
        }catch(IllegalArgumentException e)
        {
            throw new IllegalStateException(ErrorMessage.NoExist("토큰"));
        }

    }
}

