package com.infe.app.web;

import com.infe.app.domain.posts.PostsRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;                   //port는  왜 정하는거야?? 8080고정하는 것보다 좋은가?

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private TestRestTemplate restTemplate; //왜 TestRestTemplate+springBootTest(JPA?)냐

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");//문자열을 포함하는지만 확인
    }

    @Test
    public void 공지사항목록_마지막수정_날짜형식_맞춘다() throws Exception {
        //given
//        postsRepository.saveAndFlush(Posts.builder()
//                .title("집회")
//                .content("대강당으로 오세요")
//                .author("2020서기")
//                .build());
//
//        String url = "http://localhost:"+port+"/";
//
//        //when
//        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
////        assertThat(responseEntity.getBody()).isEqualTo();
//        log.info(responseEntity.getBody().toString());
    }
}
