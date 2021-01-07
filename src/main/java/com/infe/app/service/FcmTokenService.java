package com.infe.app.service;

import com.infe.app.domain.FcmToken.FcmTokenRespository;
import com.infe.app.web.dto.FcmTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class FcmTokenService {
    private final FcmTokenRespository fcmTokenRespository;

    @Transactional
    public Long insert(FcmTokenDto fcmTokenDto) throws Exception {
        return fcmTokenRespository.save(fcmTokenDto.toEntity()).getId();
    }

//    @Transactional
//    public Long update(String token){
//        FcmToken fcmToken = fcmTokenRespository.findByToken(token).orElseThrow(()->new IllegalArgumentException("존재하지 않는 토큰입니다."));
//        fcmToken.
//    }

    @Transactional(readOnly = true)
    public List<FcmTokenDto> findAll() throws Exception {
        return fcmTokenRespository.findAll()
                .stream()
                .map(FcmTokenDto::new)
                .collect(toList());
    }

    @Transactional
    public Long delete(FcmTokenDto fcmTokenDto) throws Exception {
        fcmTokenRespository.deleteByToken(fcmTokenDto.getToken());
        return 1L;
    }
}

