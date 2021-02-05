package com.infe.app.domain.participant;

import com.infe.app.domain.attandance.Attendance;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Attendee는 출석체크시 참여자가 입력하는 정보를 바탕으로 한 엔티티입니다.
 * Meeting과 연관관계를 맺습니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long studentId;

    private String name;

    private Long generation;

    @Column(unique = true)
    private String token;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "participant")
    private List<Attendance> attendances= new ArrayList<>();

    public void addAttendance(Attendance attendance) {
        if (!this.attendances.contains(attendance)) {
            this.attendances.add(attendance);
        }
    }

    public void updateToken(String token){
        this.token = token;
    }

    @Builder
    public Participant(Long studentId, String name, Long generation, String token) {
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.token = token;
    }
}
