package com.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // @Entity를 붙이면 DB에 테이블이 생성됨
@AllArgsConstructor // 생성자
@ToString
@NoArgsConstructor // 기본생성자
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에게 id가 중복되지 않도록 자동생성 요청
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


//    }

    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}
