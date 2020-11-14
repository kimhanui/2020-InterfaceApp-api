package com.infe.app.domain.meeting;

import com.infe.app.domain.member.Member;
import com.infe.app.web.dto.Meeting.MemberMeetingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    //이미 만들어진 Meeting을 찾는 메서드 (by meetingName 또는 value)
    Optional<Meeting> findMeetingByPasskey(String passKey);

    Optional<Meeting> findMeetingsByCreatedDateTime(LocalDateTime dateTime);

    @Query(value = "select m from Member m Join m.meetings mt " +
            "where mt.createdDateTime = :dateTime order By m.groupNum ASC, m.studentId ASC")
    List<Member> findMembersByDate(@Param("dateTime")LocalDateTime dateTime);

    //member와 meeting 조인 조회
    @Query(value = "select new com.infe.app.web.dto.Meeting.MemberMeetingResponseDto(m,mt) " +
            "from Member m Join m.meetings mt " +
            "order by mt.createdDateTime ASC, m.groupNum ASC, m.studentId ASC")
    List<MemberMeetingResponseDto> findAllMember();
}
