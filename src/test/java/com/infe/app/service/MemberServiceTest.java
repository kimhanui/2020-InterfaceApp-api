package com.infe.app.service;

import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @After
    public void tearDown(){
        memberRepository.deleteAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 회원중복_예외_발생한다() throws IllegalArgumentException{
        //given
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(17000000L)
                .name("lee")
                .groupNum(31L)
                .contact("010-0000-0000")
                .build();
        memberRepository.save(dto.toEntity());

        //when,then
        memberService.insert(dto);
    }

    @Test
    public void 회원_findAll_오름차순(){
        //given
        List<MemberResponseDto> dtos = memberService.findAll();
        long G=0L, ID=0L;
        long targetG, targetID;

        //when
        for(MemberResponseDto dto: dtos){
            targetG=dto.getGroupNum();
            targetID=dto.getStudentId();

            //then
            assertThat(targetG).isGreaterThanOrEqualTo(G);
            if(targetG == G){
                assertThat(targetID).isGreaterThan(ID);
            }
            else if(targetG > G){
                G = targetG;
                ID = targetID;
            }
        }
    }
}
