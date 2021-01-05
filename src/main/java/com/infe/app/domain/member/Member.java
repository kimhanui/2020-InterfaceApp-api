package com.infe.app.domain.member;

import com.infe.app.domain.BaseTimeEntity;
import com.infe.app.domain.meeting.Meeting;
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

    private String contact;

    private String phone;

    private String department;

    /**
     * 참석기록
     **/
    @Embedded
    private ManageStatus manageStatus;

    @ManyToMany(mappedBy = "members")
    private List<Meeting> meetings = new ArrayList<>();

    @Builder
    public Member(Long id, Long studentId, String name, Long generation,
                  String contact, String phone, String department, ManageStatus manageStatus) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.contact = contact;
        this.phone = phone;
        this.department = department;
        this.manageStatus = manageStatus;
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
        meeting.getMembers().add(this);
    }

    public void update(MemberRequestDto dto) {
        this.studentId = dto.getStudentId();
        this.name = dto.getName();
        this.generation = dto.getGeneration();
        this.contact = dto.getContact();
        this.phone = dto.getPhone();
        this.department = dto.getDepartment();
        this.manageStatus = dto.getManageStatus();
    }
}
