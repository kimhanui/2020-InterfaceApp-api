package com.infe.app.service;

import com.infe.app.domain.posts.PostsRepository;
import com.infe.app.web.dto.PostsListResponseDto;
import com.infe.app.web.dto.PostsResponseDto;
import com.infe.app.web.dto.PostsSaveRequestDto;
import com.infe.app.web.dto.PostsUpdateRequestDto;
import lombok.extern.java.Log;
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
public class PostsServiceTest {
    @Autowired
    private PostsRepository repo;
    @Autowired
    private PostsService service;


    public void 테스트데이터(){
        String title = "test";
        String content = "content...hahahah";
        for(int i=0; i<3;i++){
            PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                                                    .title(title+i)
                                                    .content(content+i)
                                                    .build();
            service.save(dto);
        }
    }

    @Test
    public void PostsService_findAllDesc() {
        테스트데이터();
        List<PostsListResponseDto> postsList = service.findAllDesc();
        for (PostsListResponseDto dto : postsList) {
            log.info("[" + dto.getId() + "] " + dto.getModifiedDate());
        }
    }

    @Test
    public void PostsService_update됨() {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto saveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        Long savedId = repo.saveAndFlush(saveRequestDto.toEntity()).getId();


        String expectedTitle = "expectedTitle";
        String expectedContent = "expectedContent";
        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        //when
        log.info("original: title=" + saveRequestDto.getTitle() + ", content=" + saveRequestDto.getContent());
        Long id = service.update(savedId, updateRequestDto);
        repo.flush();
        PostsResponseDto foundPostsDto = service.findById(id);
        log.info("original: title=" + foundPostsDto.getTitle() + ", content=" + foundPostsDto.getContent());

        //then
        assertThat(foundPostsDto.getTitle()).isEqualTo(foundPostsDto.getTitle());
    }


}
