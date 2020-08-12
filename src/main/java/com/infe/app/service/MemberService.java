package com.infe.app.service;

import com.infe.app.domain.member.Member;
import com.infe.app.domain.member.MemberRepository;
import com.infe.app.web.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public void insert(MemberSaveRequestDto dto){
//        Member m = dto.toEntity();
//        memberRepository.save(m);
    }
    @Transactional(readOnly = true)
    public void findAll(){

    }
    @Transactional
    public void update(){

    }
    @Transactional
    public void delete(){

    }
    @Transactional
    public void deleteAll(){

    }


}
