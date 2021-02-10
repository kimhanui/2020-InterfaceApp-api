package com.infe.app.domain.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {


    Optional<Meeting> findByPasskey(String passKey);

    @Query("select m from Meeting m order by m.createdDateTime DESC")
    List<Meeting> findAllByDesc();

    @Modifying
    @Query("delete from Meeting m where m.id in :ids")
    void deleteAllById(@Param("ids") List<Long> ids);
}
