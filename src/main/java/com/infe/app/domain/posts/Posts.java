package com.infe.app.domain.posts;

import com.infe.app.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //반드시 있어야하나?
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;


    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Builder //생성자처럼 작성.
    public Posts( String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
