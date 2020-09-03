package com.infe.app.web;

import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)//JPA기능 작동
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void Member_등록된다() throws Exception {
        //given
        Long STUDENTID = 170170L;
        String NAME = "baron";
        Long GROUPNUM = 30L;

        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(STUDENTID)
                .name(NAME)
                .groupNum(GROUPNUM)
                .build();
        String url = "http://localhost:+" + port + "/api/v1/member";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> members = memberRepository.findAll();
        assertThat(members.get(0).getStudentId()).isEqualTo(STUDENTID);
        assertThat(members.get(0).getName()).isEqualTo(NAME);
        assertThat(members.get(0).getGroupNum()).isEqualTo(GROUPNUM);
    }

    @Test
    public void Member_수정된다() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .studentId(100100L)
                .name("kim-before")
                .groupNum(1L)
                .build());
        Long targetId = savedMember.getId();
        Long expectedStudentId = 200200L;
        String expectedName = "kim-after";
        Long expedtedGroupNum = 2L;

        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .studentId(expectedStudentId)
                .name(expectedName)
                .groupNum(expedtedGroupNum)
                .build();

        String url = "http://localhost:" + port + "/api/v1/member/" + targetId;
        HttpEntity<MemberRequestDto> requestEntity = new HttpEntity<>(memberRequestDto);

        //when-업데이트 실행행
        ResponseEntity<Long> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> members = memberRepository.findAllDesc();
        assertThat(members.get(0).getStudentId()).isEqualTo(expectedStudentId);
        assertThat(members.get(0).getName()).isEqualTo(expectedName);
        assertThat(members.get(0).getGroupNum()).isEqualTo(expedtedGroupNum);
    }

    @Test
    public void Member_삭제된다() {

    }

    @Test
    public void Member_모두조회() {

    }

}