package com.infe.app.domain.attandance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    /**cascade = CascadeType.ALL로 인해 (ALL은 Delete까지 포함된 상태입니다.) 부모인 Shop을 지울때, 자식인 Shop도 같이 지워야하니 Shop 키를 기준으로 Item을 모두 가져와서 1건씩 삭제하는 것이였습니다.*/
    @Modifying
    @Query("delete from Attendance a where a.meeting.id in :meeting_id")
    void deleteAllByMeetingId(@Param("meeting_id") Long meeting_id);

    @Modifying
    @Query("delete from Attendance a where a.meeting.id in :meeting_ids")
    void deleteAllByMeetingIdList(@Param("meeting_id") List<Long> meeting_ids);

    @Query("select a from Attendance a where a.meeting.id in :meeting_id")
    List<Attendance> findAllByMeetingId(@Param("meeting_id") Long meeting_id);

    @Query("select a from Attendance a where a.participant.id in :participant_id")
    List<Attendance> findAllByParticipantId(@Param("participant_id") Long participant_id);
}
