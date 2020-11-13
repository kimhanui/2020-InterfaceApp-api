package com.infe.app.web;

import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
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

    @Test
    public void Member_등록된다() throws Exception {
        //given
        Long STUDENTID = 170170L;
        String NAME = "baron";
        Long GROUPNUM = 30L;
        String CONTACT = "1234-1234";

        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(STUDENTID)
                .name(NAME)
                .groupNum(GROUPNUM)
                .contact(CONTACT)
                .build();
        String url = "http://localhost:" + port + "/api/v1/member";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> members = memberRepository.findAll();
        int size = members.size();
        assertThat(members.get(size - 1).getStudentId()).isEqualTo(STUDENTID);
        assertThat(members.get(size - 1).getName()).isEqualTo(NAME);
        assertThat(members.get(size - 1).getGroupNum()).isEqualTo(GROUPNUM);
        assertThat(members.get(size - 1).getContact()).isEqualTo(CONTACT);
    }

    @Test
    public void Member_수정된다() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .studentId(999999L)
                .name("kim-before")
                .groupNum(1L)
                .contact("1234-1234")
                .build());
        Long targetId = savedMember.getId();
        Long expectedStudentId = 999999L;
        String expectedName = "kim-after";
        Long expectedGroupNum = 2L;
        String expectedContact = "1234-1234";

        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .studentId(expectedStudentId)
                .name(expectedName)
                .groupNum(expectedGroupNum)
                .contact(expectedContact)
                .build();

        String url = "http://localhost:" + port + "/api/v1/member/" + targetId;
        HttpEntity<MemberRequestDto> requestEntity = new HttpEntity<>(memberRequestDto);

        //when
        ResponseEntity<Long> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> members = memberRepository.findAllDesc();
        assertThat(members.get(0).getStudentId()).isEqualTo(expectedStudentId);
        assertThat(members.get(0).getName()).isEqualTo(expectedName);
        assertThat(members.get(0).getGroupNum()).isEqualTo(expectedGroupNum);
        assertThat(members.get(0).getContact()).isEqualTo(expectedContact);
    }
}