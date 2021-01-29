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

    /**
     * 입력이 리스트로 변경됐으니(데이터 큼)
     * sql 전달 전 미리 예외 catch해서 정상적인 입력만 insert해야할듯
     **/
    @Transactional
    public String insertAll(List<MemberRequestDto> dtos) {
        memberRepository.saveAll(dtos.stream().map(MemberRequestDto::toEntity).collect(Collectors.toList()));
        return "정상적으로 저장됐습니다.";
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() throws NullPointerException {
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
    public String deleteAll() throws Exception {
        memberRepository.deleteAll();
        return "정상적으로 삭제됐습니다.";
    }
}
