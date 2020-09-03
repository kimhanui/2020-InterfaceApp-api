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
    private Long groupNum;

    public Member toEntity(){
        return Member.builder()
                .studentId(studentId)
                .name(name)
                .groupNum(groupNum)
                .build();
    }

    @Builder
    public MemberRequestDto(Long studentId, String name, Long groupNum){
        this.studentId = studentId;
        this.name = name;
        this.groupNum = groupNum;
    }
}