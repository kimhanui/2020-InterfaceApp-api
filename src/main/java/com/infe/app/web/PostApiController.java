package com.infe.app.web;

import com.infe.app.service.PostsService;
import com.infe.app.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postsService;

    /**나랑다른점: 조회 관련은 그냥 IndexController
     * -> 어떤 이벤트가 아니라 페이지로딩시 바로 보여줘야돼서 그럴수도
     * */
    //url에 insert, update,,넣지 않고 HttpMethod로 Post, Put, Get, Delete구분...
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/member/list")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAllDesc();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void delete(@PathVariable Long id){
        postsService.delete(id);
    }

    @DeleteMapping("/api/v1/posts/all")
    public void deleteAll(){
        postsService.deleteAll();
    }
}
