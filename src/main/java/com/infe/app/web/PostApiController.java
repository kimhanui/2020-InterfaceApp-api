package com.infe.app.web;

import com.infe.app.service.PostsService;
import com.infe.app.web.dto.PostsListResponseDto;
import com.infe.app.web.dto.PostsResponseDto;
import com.infe.app.web.dto.PostsSaveRequestDto;
import com.infe.app.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postsService;

    //url에 insert, update,,넣지 않고 HttpMethod로 Post, Put, Get, Delete구분...
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto)throws Exception {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) throws Exception{
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)throws Exception{
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() throws Exception{
        return postsService.findAllDesc();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id)throws Exception{
        return postsService.delete(id);
    }

    @DeleteMapping("/api/v1/posts/all")
    public Long deleteAll()throws Exception{
        return postsService.deleteAll();
    }
}
