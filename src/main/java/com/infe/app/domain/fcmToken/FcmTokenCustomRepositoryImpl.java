package com.infe.app.domain.fcmToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class FcmTokenCustomRepositoryImpl implements FcmTokenCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public void deleteByToken(String token) {

    }
}
