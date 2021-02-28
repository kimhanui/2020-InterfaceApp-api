package com.infe.app.web.dto;

import com.infe.app.domain.member.ManageStatus;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@ToString
@NoArgsConstructor
@Getter
public class MemberRequestDto {
    @NotNull
    private Long studentId;

    @NotBlank
    private String name;

    @NotNull
    private Long generation;

    private String contact;

    private String phone;

    private String department;

    private String state;

    private Map<String, String> manageStatus;

    public Member toEntity() {
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .contact(contact)
                .phone(phone)
                .department(department)
                .state(State.valueOfLabel(state))
                .manageStatus(new ManageStatus(manageStatus))
                .build();
    }

    @Builder
    public MemberRequestDto(Long studentId,
                            String name,
                            Long generation,
                            String contact,
                            String phone,
                            String department,
                            String state,
                            Map<String, String> manageStatus) {
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.contact = contact;
        this.phone = phone;
        this.department = department;
        this.state = state;
        this.manageStatus = manageStatus;
    }
}
