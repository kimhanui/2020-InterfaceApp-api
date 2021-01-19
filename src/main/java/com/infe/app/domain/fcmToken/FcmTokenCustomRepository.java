package com.infe.app.domain.fcmToken;

/**이 인터페이스를 구현한 접미사가 Impl 인 클래스를 작성하면
 * 이 Repository를 쓸 때 해당 클래스의 구현체가 자동적으로 할당되어 사용된다.
 */
public interface FcmTokenCustomRepository<T> {
    void deleteByToken(String token);
}
