package com.infe.app.web.dto.Meeting;

import com.infe.app.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckedMemberResponseDto {
    private Long studentId;
    private String name;
    private Long generation;

    public CheckedMemberResponseDto(Member member){
        this.studentId = member.getStudentId();
        this.name = member.getName();
        this.generation=member.getGeneration();
    }
}
