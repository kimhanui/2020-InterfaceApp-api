package com.infe.app.domain.attendee;

import com.infe.app.domain.meeting.Meeting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Attendee는 출석체크시 참여자가 입력하는 정보를 바탕으로 한 엔티티입니다.
 * Meeting과 연관관계를 맺습니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private String name;

    private Long generation;

    private String token;

    @JoinTable(name = "attendee_meeting"
            , joinColumns = {@JoinColumn(name = "attendee_id")}
            , inverseJoinColumns = {@JoinColumn(name = "meeting_id")})
    @ManyToMany
    private List<Meeting> meetings = new ArrayList<>();

    public void addMeeting(Meeting meeting) {
        if (!this.meetings.contains(meeting)) {
            this.meetings.add(meeting);
            if (!meeting.getAttendees().contains(this))
                meeting.getAttendees().add(this);
        }
    }

    public void updateToken(String token){
        this.token = token;
    }

    @Builder
    public Attendee(Long studentId, String name, Long generation, String token) {
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.token = token;
    }
}
