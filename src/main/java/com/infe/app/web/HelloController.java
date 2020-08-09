package com.infe.app.web;

import com.infe.app.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello/dto")
    public HelloResponseDto hello(@RequestParam("name") String name,
                        @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
