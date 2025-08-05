package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Repository와 테스트를 하겠다는 명시
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("1번 게시글_조회하여_테스트") // 테스트 결과 이름 출력
    void findByArticleId() {
        // 6번 게시글을 조회하는 댓글 조회
        Long articleId = 1L;

        // 1. expected - 예상 데이터 작성
        Article article = new Article(1L, "1111", "1111");
        List<Comment> expected = Arrays.asList();

        // 2. 실제 데이터 수집
        List<Comment> commentList = commentRepository.findByArticleId(articleId);

        // 3. 비교
        assertEquals(expected.toString(), commentList.toString(), "1번 게시글은 댓글 없음");

    }

//    @Test
//    @DisplayName("특정_아이디_조회하여_테스트") // 테스트 결과 이름 출력
//    void findByArticleId() {
//        // 6번 게시글을 조회하는 댓글 조회
//        Long articleId = 6L;
//
//        // 1. expected - 예상 데이터 작성
//        Article article = new Article(6L, "나의 장점은?", "댓글 ㄱㄱ");
//        Comment fir = new Comment(7L, article, "G", "코딩 빼고 다 잘함");
//        Comment sec = new Comment(8L, article, "B", "밈 잘 따라함ㅋㅋㅋ");
//        Comment thr = new Comment(9L, article, "E", "난 장점 없는 게 장점인 듯ㅎㅎㅎ");
//        List<Comment> expected = Arrays.asList(fir, sec, thr);
//
//        // 2. 실제 데이터 수집
//        List<Comment> commentList = commentRepository.findByArticleId(articleId);
//
//        // 3. 비교
//        assertEquals(expected.toString(), commentList.toString());
//
//    }

    @Test
    @DisplayName("특정 닉네임으로 댓글 조회")
    void findByNickname() {
        String nickname = "A";

        // 예상 데이터
        Comment fir = new Comment(1L, new Article(4L, "나의 최애는?", "댓글 ㄱㄱ"), nickname, "역시 두산이지");
        Comment sec = new Comment(4L, new Article(5L, "나의 인생 언어는?", "댓글 ㄱㄱ"), nickname, "한국어가 인생언어지");
        List<Comment> expected = Arrays.asList(fir, sec);

        // 실제 데이터
        List<Comment> commentList = commentRepository.findByNickname(nickname);

        // 비교
        assertEquals(expected.toString(), commentList.toString());
    }
}