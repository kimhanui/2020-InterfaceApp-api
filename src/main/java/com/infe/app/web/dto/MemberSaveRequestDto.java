package com.infe.app.web.dto;

import com.infe.app.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSaveRequestDto {

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
}
