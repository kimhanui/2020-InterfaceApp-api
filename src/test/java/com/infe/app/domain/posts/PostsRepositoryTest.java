package com.infe.app.domain.posts;

import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After//이거 괜찮네!
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2020, 8, 10, 0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        log.info(">>>>>>>>> createDate="+posts.getCreatedDateTime()+", modifiedDate="+posts.getModifiedDateTime());

        assertThat(posts.getCreatedDateTime()).isAfter(now);
        assertThat(posts.getModifiedDateTime()).isAfter(now);

    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        int size = postsList.size();
        Posts posts = postsList.get(size-1);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
