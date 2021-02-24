package com.infe.app.service;

import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Long insert(MemberRequestDto dto) throws IllegalArgumentException {
        Optional<Member> member = memberRepository.findByStudentId(dto.getStudentId());
        member.ifPresent(val -> {
            throw new IllegalArgumentException(ErrorMessage.AlreadyExist("회원"));
        });
        return memberRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(MemberRequestDto dto) throws IllegalArgumentException {
        Member member = memberRepository.findByStudentId(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("회원")));
        member.update(dto);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto find(Long id) throws IllegalArgumentException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("회원")));
        return new MemberResponseDto(member);
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
    public Long delete(Long id) throws IllegalArgumentException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("회원")));
        memberRepository.deleteById(id);
        return id;
    }

    @Transactional
    public String deleteAll() throws Exception {
        memberRepository.deleteAll();
        return "정상적으로 삭제됐습니다.";
    }
}
