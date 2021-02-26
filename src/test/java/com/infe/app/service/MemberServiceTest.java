package com.infe.app.service;

import com.infe.app.domain.member.ManageStatus;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.domain.member.State;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void deleteAll시_쿼리로그(){
        //when.then
        memberRepository.deleteAll();
    }

    @Test
    public void deleteAllinBatch시_쿼리로그(){
        //when,then
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void DB에저장할때_참석여부_boolean에서_YN로_변환됨() {
        //given
        ManageStatus manageStatus = new ManageStatus();
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(17000000L)
                .name("lee")
                .generation(31L)
                .contact("kim@gmail.com")
                .phone("010-1111-2222")
                .state(State.MILITARY.getValue())
                .manageStatus(manageStatus.toDto())
                .build();

        //when
        memberRepository.save(dto.toEntity());

        //then
        Member target = memberRepository.findByStudentId(17000000L).get();
        log.info(target.getManageStatus().toString());
        assertThat(target.getManageStatus().isFirstDues()).isEqualTo(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 회원_update_학번중복예외발생(){
        //given
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(200200L)
                .name("lee")
                .generation(31L)
                .contact("kim@gmail.com")
                .phone("010-1111-2222")
                .state(State.MILITARY.getValue())
                .manageStatus(new ManageStatus().toDto())
                .build();

        //when, then
        memberService.update(1L, dto);
    }

    @Test
    public void 회원_findAll_오름차순() {
        //given
        List<MemberResponseDto> dtos = memberService.findAll();
        long G = 0L, ID = 0L;
        long targetG, targetID;

        //when
        for (MemberResponseDto dto : dtos) {
            targetG = Long.valueOf(dto.getGeneration());
            targetID = Long.valueOf(dto.getStudentId());

            //then
            assertThat(targetG).isGreaterThanOrEqualTo(G);
            if (targetG == G) {
                assertThat(targetID).isGreaterThan(ID);
            } else if (targetG > G) {
                G = targetG;
                ID = targetID;
            }
        }
    }

}
