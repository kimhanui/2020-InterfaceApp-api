package com.infe.app.web.dto;

import com.infe.app.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private Long studentId;
    private String name;
    private Long generation;
    private String contact;

    public MemberResponseDto(Member m) {
        this.id = m.getId();
        this.studentId = m.getStudentId();
        this.name = m.getName();
        this.generation = m.getGeneration();
        this.contact = m.getContact();
    }

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .contact(contact)
                .build();
    }
}