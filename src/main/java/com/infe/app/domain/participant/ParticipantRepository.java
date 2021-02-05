package com.infe.app.domain.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByStudentId(Long studentId);

    @Modifying
    @Query("delete from Participant p where p.id in :ids")
    void deleteAllById(@Param("ids") List<Long> ids);
}
