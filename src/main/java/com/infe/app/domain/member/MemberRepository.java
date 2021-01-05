package com.infe.app.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m Order By m.generation ASC, m.studentId ASC")
    List<Member> findAllAsc();

    Optional<Member> findByStudentId(Long studentId);
}
