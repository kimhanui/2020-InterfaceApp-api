package com.infe.app.domain.attandance;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.participant.Participant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor


@Getter
@Entity
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @CreatedDate
    private LocalDateTime attendanceDateTime;

    @Builder
    public Attendance(Participant participant, Meeting meeting){
        this.participant = participant;
        this.meeting = meeting;
        this.attendanceDateTime = LocalDateTime.now();
    }
}
