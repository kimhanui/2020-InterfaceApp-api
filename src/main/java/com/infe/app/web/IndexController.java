package com.infe.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //뷰 나타낼 용이라 ㅇㅇ
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        return "index"; //자동으로 앞-경로, 뒤-확장자(mustache)가 붙음
    }
}
