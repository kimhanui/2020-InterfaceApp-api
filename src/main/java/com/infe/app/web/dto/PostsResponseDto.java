package com.infe.app.web.dto;

import com.infe.app.domain.posts.Posts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
