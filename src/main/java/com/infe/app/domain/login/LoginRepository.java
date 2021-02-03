package com.infe.app.domain.login;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    @Query("select e from Login e where e.pw=:pw")
    Optional<Login> findByPassword(@Param("pw") String pw);

    @Query("select e from Login e where e.role=:role")
    Optional<Login> findByRole(@Param("role") Login.Role role);
}
