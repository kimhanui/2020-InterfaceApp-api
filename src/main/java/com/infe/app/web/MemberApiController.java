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
@RestController
public class MemberApiController {
    public final MemberService memberService;

    @PostMapping("/api/v1/member")
    public Long insert(@RequestBody MemberRequestDto dto) {
        return memberService.insert(dto);
    }

    @PutMapping("/api/v1/member/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberRequestDto dto) {
        return memberService.update(id, dto);
    }

    @GetMapping("/api/v1/member/{id}")
    public MemberResponseDto find(@PathVariable Long id) {
        return memberService.find(id);
    }

    @GetMapping("/api/v1/member/list")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

    @DeleteMapping("/api/v1/member/{id}")
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }


}
