package com.infe.app.service;

import com.infe.app.domain.posts.Posts;
import com.infe.app.domain.posts.PostsRepository;
import com.infe.app.service.ErrorMessage.ErrorMessage;
import com.infe.app.web.dto.PostsListResponseDto;
import com.infe.app.web.dto.PostsResponseDto;
import com.infe.app.web.dto.PostsSaveRequestDto;
import com.infe.app.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
        //save하고 getId하면 INSERT, FIND 2쿼리
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) throws IllegalArgumentException{
        Posts posts = postsRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NoExist("게시글")));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id)throws IllegalArgumentException {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NoExist("게시글")));

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true) //조회에서는 tx범위는 유지하되 조회속도가 개선되므로 똑같이 사용권장.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //메소드에 넣어 새로운 타입을 가져와 매핑
                .collect(Collectors.toList());
    }

    @Transactional
    public Long delete(Long id) throws IllegalArgumentException {
        try {
            postsRepository.deleteById(id);
            return id;
        }catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException(ErrorMessage.NoExist("게시글"));
        }
    }

    @Transactional
    public Long deleteAll(){
        postsRepository.deleteAll();
        return 1L;
    }

}
