package com.infe.app.domain.member;

import com.infe.app.domain.BaseTimeEntity;
import com.infe.app.web.dto.MemberRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Member은 회원 명부 관리에만 사용됩니다.
 * 따라서 Meeting 엔티티와 연관관계를 맺지 않습니다.
 */
@ToString
@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long studentId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Long generation;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    private String contact;

    private String phone;

    private String department;

    @Embedded
    private ManageStatus manageStatus;

    @Builder
    public Member(Long id, Long studentId, String name, Long generation,
                  String contact, String phone, String department, State state, ManageStatus manageStatus) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.contact = contact;
        this.phone = phone;
        this.department = department;
        this.state = (state != null) ? state : State.ATTENDING;
        this.manageStatus = manageStatus;
    }

    public void update(MemberRequestDto dto) {
        this.studentId = dto.getStudentId();
        this.name = dto.getName();
        this.generation = dto.getGeneration();
        this.contact = dto.getContact();
        this.phone = dto.getPhone();
        this.department = dto.getDepartment();
        this.state = State.valueOfLabel(dto.getState());
        this.manageStatus = new ManageStatus(dto.getManageStatus());
    }
}
