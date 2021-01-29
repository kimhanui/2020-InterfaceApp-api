package com.infe.app.web.dto;

import com.infe.app.domain.member.ManageStatus;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.State;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.Map;

@Log
@ToString
@Getter
public class MemberResponseDto {
    private Long id;
    private String studentId;
    private String name;
    private String generation;
    private String contact;
    private String phone;
    private String department;
    private String state;
    private Map<String, String> manageStatus;

    public MemberResponseDto(Member m) {
        this.id = m.getId();
        this.studentId = Long.toString(m.getStudentId());
        this.name = m.getName();
        this.generation = Long.toString(m.getGeneration());
        this.contact = m.getContact();
        this.phone = m.getPhone();
        this.department = m.getDepartment();
        this.state = m.getState().getValue();
        this.manageStatus = m.getManageStatus().toDto();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .studentId(Long.valueOf(studentId))
                .name(name)
                .generation(Long.valueOf(generation))
                .contact(contact)
                .phone(phone)
                .department(department)
                .state(State.valueOfLabel(state))
                .manageStatus(new ManageStatus(manageStatus))
                .build();
    }

}