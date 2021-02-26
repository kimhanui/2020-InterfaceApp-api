package com.infe.app.web;

import com.infe.app.service.MemberService;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/**")
@RestController
public class MemberApiController {
    public final MemberService memberService;

    @PostMapping
    public Long insert(@Valid @RequestBody MemberRequestDto memberRequestDto)throws Exception{
        return memberService.insert(memberRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @Valid @RequestBody MemberRequestDto memberRequestDto)throws Exception{
        return memberService.update(id, memberRequestDto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto find(@PathVariable Long id) throws Exception{
        return memberService.find(id);
    }

    @GetMapping("/list")
    public List<MemberResponseDto> findAll()throws Exception {
        List<MemberResponseDto> dtos = memberService.findAll();
        return dtos;
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) throws Exception{
        return memberService.delete(id);
    }

}
