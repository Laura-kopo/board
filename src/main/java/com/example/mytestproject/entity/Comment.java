package com.example.mytestproject.entity;

import com.example.mytestproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne // 댓글과 게시글의 다대일 관계 설정
    @JoinColumn(name="article_id") // FK 생성. 컬럼 이름을 article_id로 지정. 자연스럽게 article의 PK(id)에 Mapping
    private Article article; // 참조하는 게시글

    @Column
    private String nickname; // 댓글 작성자
    @Column
    private String body; // 댓글 내용

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외가 발생한다면 예외 처리(= Entity로 변환이 불가능한 상황)
        if(dto.getId() != null) throw new IllegalArgumentException("댓글 생성에 실패하였습니다. ID는 전달되어서는 안됩니다.");

        if (dto.getArticleId() != article.getId()) throw new IllegalArgumentException("게시글의 ID와 생성할 댓글 내 ID가 서로 다릅니다.");

        // Entity를 생성
        return new Comment(dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody());
    }

    public Long getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBody() {
        return body;
    }

    public void patch(CommentDto dto) {
        // 예외 상황 발생 처리
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패. 요청 시도한 댓글 ID와 원본 ID가 다름");

        // 객체 갱신하기
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname();

        if(dto.getBody() != null)
            this.body = dto.getBody();

    }
}
