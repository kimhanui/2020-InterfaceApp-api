package com.infe.app.domain.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {


    Optional<Meeting> findByPasskey(String passKey);

    @Query("select m from Meeting m order by m.createdDateTime DESC")
    List<Meeting> findAllByDesc();
}
