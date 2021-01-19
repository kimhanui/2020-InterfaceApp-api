package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Transactional
    public Long insert(MemberRequestDto dto) throws IllegalArgumentException {
        Optional<Member> member = memberRepository.findByStudentId(dto.getStudentId());

        //중복검사하기(학번)
        member.ifPresent(val -> {throw new IllegalArgumentException("이미 존재하는 회원입니다.");});

        try {
            Member m = dto.toEntity();
            return memberRepository.save(m).getId();
        }catch(DataIntegrityViolationException e){
            throw new NullPointerException("입력되지 않은 항목이 있습니다: "+dto.getBlank().toString());
        }
    }

    @Transactional
    public Long update(Long id, MemberRequestDto dto) throws IllegalArgumentException {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));

        m.update(dto);
        return m.getId();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto find(Long id) throws IllegalArgumentException {
        Member m = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));
        return new MemberResponseDto(m);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
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
        //meeting 연관관계 다 제거 후
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));
        List<Meeting> meetings = member.getMeetings();
        for (Meeting meeting : meetings) {
            meeting.getMembers().remove(member);
        }

        memberRepository.deleteById(id);
        return id;
    }


}
