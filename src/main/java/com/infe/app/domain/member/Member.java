package com.infe.app.domain.member;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.BaseTimeEntity;
import com.infe.app.web.dto.MemberRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Long generation; //기수

    @Column
    private String contact;

    @Column
    private String phone;

    @Column
    private String department;

    @Embedded
    private ManageStatus manageStatus;

    @ManyToMany(mappedBy = "members")
    private List<Meeting> meetings = new ArrayList<>();

    @Builder
    public Member(Long id, Long studentId, String name, Long generation,
                  String contact) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.contact = contact;
    }

    public void addMeeting(Meeting meeting){
        meetings.add(meeting);
        meeting.getMembers().add(this);
    }

    public void update(MemberRequestDto dto) {
        this.studentId = dto.getStudentId();
        this.name =dto.getName();
        this.generation = dto.getGeneration();
        this.contact = dto.getContact();
    }
}
