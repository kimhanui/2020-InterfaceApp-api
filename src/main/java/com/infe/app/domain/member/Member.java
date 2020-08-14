package com.infe.app.domain.member;

import com.infe.app.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Long groupNum; //기수

    @Builder
    public Member(Long id, Long studentId, String name, Long groupNum){
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.groupNum = groupNum;
    }

}