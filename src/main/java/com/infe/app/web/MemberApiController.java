package com.infe.app.web;

import com.infe.app.service.MemberService;
import com.infe.app.web.dto.LoginRequestDto;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/**")
@RestController
public class MemberApiController {
    public final MemberService memberService;

    @PostMapping
    public Long insert(@RequestBody MemberRequestDto dto) throws Exception{
        return memberService.insert(dto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberRequestDto dto)throws Exception {
        return memberService.update(id, dto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto find(@PathVariable Long id)throws Exception {
        return memberService.find(id);
    }

    @GetMapping("/list")
    public List<MemberResponseDto> findAll()throws Exception {
        return memberService.findAll();
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id)throws Exception {
        return memberService.delete(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        return memberService.login(loginRequestDto).toString();
    }
}
