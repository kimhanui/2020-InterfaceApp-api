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

import java.util.ArrayList;
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
        Long STUDENTID = 99999L;
        String NAME = "baron";
        Long GROUPNUM = 30L;
        String CONTACT = "1234-1234";

        List<MemberRequestDto> dtos = new ArrayList<>();
        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(STUDENTID)
                .name(NAME)
                .generation(GROUPNUM)
                .contact(CONTACT)
                .build();
        dtos.add(dto);
        String url = "http://localhost:" + port + "/api/v1/member";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dtos, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Member resultMember = memberRepository.findByStudentId(99999L).get();
        assertThat(resultMember.getName()).isEqualTo(NAME);
        assertThat(resultMember.getGeneration()).isEqualTo(GROUPNUM);
        assertThat(resultMember.getContact()).isEqualTo(CONTACT);
    }


}