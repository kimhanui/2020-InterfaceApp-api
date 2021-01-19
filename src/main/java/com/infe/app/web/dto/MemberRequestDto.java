package com.infe.app.web.dto;

import com.infe.app.domain.member.ManageStatus;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Log
@NoArgsConstructor
@Getter
public class MemberRequestDto {

    private Long studentId;
    private String name;
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

    public List<String> getBlank(){
        List<String> blanks = new ArrayList<>();
        if(studentId == null)blanks.add("studentId");
        if(name == null) blanks.add("name");
        if(generation == null) blanks.add("generation");
        return blanks;
    }

    @Builder
    public MemberRequestDto(Long studentId,
            String name,
            Long generation,
            String contact,
            String phone,
            String department,
            String state,
            Map<String, String> manageStatus){
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
