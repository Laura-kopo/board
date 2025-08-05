package com.example.mytestproject.service;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // 사용할 클래스 패키지 예비로 import

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void findAll() {
        // 예상되는 데이터 작성
        Article first = new Article(1L, "1111", "1111");
        Article second = new Article(2L, "2222", "2222");
        Article third = new Article(3L, "3333", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(first, second, third));

        // 실제 DB 데이터 수집
        List<Article> articleList = articleService.findAll();

        // 예상 데이터 == 수집 데이터 비교
        assertEquals(expected.toString(), articleList.toString());

    }

    @Test
    void articles_success() {
        // 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "1111", "1111");

        // 실제 데이터
        Article article = articleService.articles(id);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }
    @Test
    void articles_fail() {
        // 예상 데이터
        Long id = -1L;
        Article expected = null;

        // 실제 데이터
        Article article = articleService.articles(id);

        // 비교
        assertEquals(expected, article);

        // Test의 결과는 내가 예상한 결과가 나와야 하기 때문에, 내가 null로 expected했으니까, 실제 결과도 null로 test 성공이 나오는 게 맞음
        // 내가 expected한 결과와 실제 결과가 다를 때 test fail이 나옴

    }

    @Test
    void create_success() {
        // 예상 데이터
        String title = "3333";
        String content = "3333";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제 데이터
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }
    @Test
    @Transactional
    void create_fail_dto에_id가_존재하는_실패테스트() {
        //예상 데이터
        Long id = 4L;
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        //실제 데이터
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected, article);
    }
}