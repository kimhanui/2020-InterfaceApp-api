package com.infe.app.web.dto;

import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberRequestDto {

    private Long studentId;
    private String name;
    private Long generation;
    private String contact;

    public Member toEntity(){
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .generation(generation)
                .contact(contact)
                .build();
    }

    @Builder
    public MemberRequestDto(Long studentId, String name, Long generation, String contact){
        this.studentId = studentId;
        this.name = name;
        this.generation = generation;
        this.contact = contact;
    }
}
