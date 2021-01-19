package com.infe.app.web;

import com.infe.app.service.MemberService;
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
        log.info(dto.toString());
        return memberService.insert(dto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberRequestDto dto)throws Exception {
        return memberService.update(id, dto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto find(@PathVariable Long id)throws Exception {
        MemberResponseDto dto = memberService.find(id);
        return dto;
    }

    @GetMapping("/list")
    public List<MemberResponseDto> findAll()throws Exception {
        List<MemberResponseDto> dtos = memberService.findAll();
        return dtos;
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id)throws Exception {
        return memberService.delete(id);
    }
}
