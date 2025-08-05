package com.example.mytestproject.dto;

import com.example.mytestproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class ArticleForm {
    private Long id; // 추가함
    private String title;
    private String content;


    public Article toEntity() {

        return new Article(id, title, content);
        // id는 자동으로 증가하기 때문에 null로 두면 됨
    }

}
