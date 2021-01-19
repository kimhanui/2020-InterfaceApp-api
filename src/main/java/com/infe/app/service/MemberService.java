package com.infe.app.service;

import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    public Long insertAll(List<MemberRequestDto> dtos){
        memberRepository.saveAll(dtos.stream().map(MemberRequestDto::toEntity).collect(Collectors.toList()));
        return 1L;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() throws NullPointerException{
        try {
            return memberRepository.findAllAsc()
                    .stream()
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());

        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public Long deleteAll() throws Exception{
        memberRepository.deleteAll();
        return 1L;
    }
}
