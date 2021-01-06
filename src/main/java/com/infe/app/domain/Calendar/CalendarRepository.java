package com.infe.app.domain.Calendar;

import com.infe.app.domain.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
