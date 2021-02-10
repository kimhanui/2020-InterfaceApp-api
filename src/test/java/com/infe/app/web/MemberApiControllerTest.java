package com.infe.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@Log
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
        Long GENERATION = 30L;
        String CONTACT = "1234-1234";

        MemberRequestDto dto = MemberRequestDto.builder()
                .studentId(STUDENTID)
                .name(NAME)
                .generation(GENERATION)
                .contact(CONTACT)
                .state("졸업")
                .department("컴공")
                .build();
        MemberRequestDto[] dtos = new MemberRequestDto[]{dto};
        ObjectMapper objectMapper = new ObjectMapper();         //dto를 json형식으로 바꿔줌
        String stringdtos = objectMapper.writeValueAsString(dtos);
        String input = "{\"collection\":"+stringdtos+"}";

        log.info(input.toString());
        String url = "http://localhost:" + port + "/api/v1/member";

        // when
        HttpHeaders headers = new HttpHeaders(); //헤더 추가: text/plain;charset=UTF-8 인코딩용
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        HttpEntity<String> request = new HttpEntity<>(input, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);


        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().length()).isGreaterThan(0);

        Member resultMember = memberRepository.findByStudentId(99999L).get();
        assertThat(resultMember.getName()).isEqualTo(NAME);
        assertThat(resultMember.getGeneration()).isEqualTo(GENERATION);
        assertThat(resultMember.getContact()).isEqualTo(CONTACT);
    }


}