package com.infe.app.service;

import com.infe.app.domain.meeting.Meeting;
import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberRequestDto;
import com.infe.app.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long insert(MemberRequestDto dto) { //중복검사하기
        Member m = dto.toEntity();
        return memberRepository.save(m).getId();
    }

    @Transactional
    public Long update(Long id, MemberRequestDto dto) {
        Member m = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));

        m.update(dto);
        return m.getId();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto find(Long id) {
        Member m = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));

        return new MemberResponseDto(m);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAllDesc().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) throws IllegalArgumentException {
        log.info("(service) id=" + id);

        //meeting 연관관계 다 제거 후
        Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾는 회원이 없습니다."));
        List<Meeting> meetings = member.getMeetings();

        for (Meeting meeting: meetings) {
            log.info("id="+meeting.getId()+"paaskey="+meeting.getPasskey());
            meeting.getMembers().remove(member);
        }

        memberRepository.deleteById(id);
    }

}
