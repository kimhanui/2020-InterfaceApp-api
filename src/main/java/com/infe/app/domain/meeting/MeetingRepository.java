package com.infe.app.domain.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {


    Optional<Meeting> findByPasskey(String passKey);

    @Query("select m from Meeting m order by m.createdDateTime DESC")
    List<Meeting> findAllByDesc();

    //member와 meeting 조인 조회
//    @Query(value = "select new com.infe.app.web.dto.Meeting.AttendeeMeetingResponseDto(m,mt) " +
//            "from Member m Join m.meetings mt " +
//            "order by mt.createdDateTime ASC, m.generation ASC, m.studentId ASC")
//    List<AttendeeMeetingResponseDto> findAllMember();
}
