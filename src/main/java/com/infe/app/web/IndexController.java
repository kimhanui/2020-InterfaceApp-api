//package com.infe.app.web;
//
//import com.infe.app.service.PostsService;
//import com.infe.app.web.dto.PostsListResponseDto;
//import com.infe.app.web.dto.PostsResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.java.Log;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@Log
//@RequiredArgsConstructor
//@Controller //뷰 나타낼 용이라 ㅇㅇ
//public class IndexController {
//
//    private final PostsService postsService;
//
//    @GetMapping("/")
//    public String index(Model model) { //Model: 서버 템플릿엔진(mustache)에서 사용할 수 있는 객체를 저장할 수 있음.
//        List<PostsListResponseDto> list = postsService.findAllDesc();
//        PostsListResponseDto dto = list.get(0);
//        log.info("modifiedDate= "+dto.getModifiedDate());
//        model.addAttribute("posts", list);
//
//        return "index"; //자동으로 앞-경로, 뒤-확장자(mustache)가 붙음
//    }
//
//    @GetMapping("/posts/save")
//    public String postsSave() {
//        return "posts-save";
//    }
//
//    @GetMapping("/posts/update/{id}")
//    public String postsUpdate(@PathVariable Long id, Model model) {
//        PostsResponseDto dto = postsService.findById(id);
//        model.addAttribute("post", dto); //dto 자체 넘겨줌
//        return "posts-update";
//    }
//}
