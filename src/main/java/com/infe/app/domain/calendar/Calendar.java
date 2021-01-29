package com.infe.app.domain.calendar;

import com.infe.app.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    private String title;
    private String content;

    public void update(Calendar calendar){
        this.date = calendar.getDate();
        this.title = calendar.getTitle();
        this.content = calendar.getContent();
    }

    @Builder
    public Calendar(LocalDate date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }
}
