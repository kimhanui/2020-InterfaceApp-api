package com.infe.app.domain.FcmToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FcmTokenRespository extends JpaRepository<FcmToken,Long> {//}, FcmTokenCustomRepository<FcmToken>{{

    @Query("select t from FcmToken t where t.token=:token")
    Optional<FcmToken> findByToken(@Param("token") String token);

    void deleteByToken(@Param("token") String token) throws IllegalArgumentException;
}
