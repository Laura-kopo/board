package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 모든 특정 게시글에 달린 댓글 전부 조회
    @Query(value="SELECT * FROM COMMENT WHERE article_id = :articleId", nativeQuery = true) // JPQL(Java Persistance Query Language)
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임이 작성한 댓글 전부 조회
    List<Comment> findByNickname(String nickname); // 외부 파일에 작성된 쿼리문 인식 = native query XML

}
