package com.infe.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller //뷰 나타낼 용이라 ㅇㅇ
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; //자동으로 앞-경로, 뒤-확장자(mustache)가 붙음
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
