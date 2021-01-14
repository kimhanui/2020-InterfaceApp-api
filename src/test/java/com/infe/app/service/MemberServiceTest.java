package com.infe.app.service;

import com.infe.app.domain.FcmToken.FcmToken;
import com.infe.app.domain.FcmToken.FcmTokenRespository;
import com.infe.app.domain.member.ManageStatus;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

    @Autowired
    FcmTokenRespository fcmTokenRespository;

    @Autowired
    FcmTokenService fcmTokenService;

    @After
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    public void 참석여부_BooleanToYNConverter_정상작동() {
        //given
        ManageStatus manageStatus = new ManageStatus(ManageStatus.State.ATTENDING);
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(17000000L)
                .name("lee")
                .generation(31L)
                .contact("kim@gmail.com")
                .phone("010-1111-2222")
                .manageStatus(manageStatus)
                .build();

        //when
        memberRepository.save(dto.toEntity());

        //then
        Member target = memberRepository.findByStudentId(17000000L).get();
        log.info(target.getManageStatus().toString());
        assertThat(target.getManageStatus().isFirstDues()).isEqualTo(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 회원중복_예외_발생한다() throws IllegalArgumentException {
        //given
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(17000000L)
                .name("lee")
                .generation(31L)
                .contact("kim@gmail.com")
                .phone("010-1111-2222")
                .build();
        memberRepository.save(dto.toEntity());

        //when,then
        memberService.insert(dto);
    }

    @Test
    public void 회원_findAll_오름차순() {
        //given
        List<MemberResponseDto> dtos = memberService.findAll();
        long G = 0L, ID = 0L;
        long targetG, targetID;

        //when
        for (MemberResponseDto dto : dtos) {
            targetG = dto.getGeneration();
            targetID = dto.getStudentId();

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

    @Test(expected = IllegalArgumentException.class)
    public void 토큰으로_삭제_정상작동() throws Exception {
        //given
        String token = "abcdefg12345";
        FcmToken fcmToken = new FcmToken(token);
        fcmTokenService.insert(token);

        //when,then
        assertThat(fcmTokenRespository.findByToken(token).get().getToken()).isEqualTo(token);

        fcmTokenRespository.deleteByToken(token);
        FcmToken res = fcmTokenRespository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));
    }
}
