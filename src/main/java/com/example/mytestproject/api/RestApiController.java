package com.example.mytestproject.api;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RestApiController {
    private static final Logger log = LogManager.getLogger(RestApiController.class);
    @Autowired
    private ArticleService articleService; // 의존성 주입
    // 인터페이스는 구현체를 구현해야 함

    @GetMapping("/api/AllArticles")
    public List<Article> AllArticles() {
        // 게시물 데이터 리턴 --> 게시물은 복수 --> List<>,
        // 데이터 위치는 DB 안
        // DB 접근 방법은 Repository 초기화
        return articleService.findAll();
    }

    @GetMapping("api/articles/{id}")
    public Article articles(@PathVariable Long id) {
        return articleService.articles(id);
    }

    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article serviceRes = articleService.create(dto);
        // 생성이 성공적이었을 때 / 생성이 실패했을 때
        // 삼항연산자 사용
        return (serviceRes != null) ? ResponseEntity.status(HttpStatus.OK).body(serviceRes)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article patchData = articleService.patch(id, dto);

        return (patchData != null) ?
                ResponseEntity.status(HttpStatus.OK).body(patchData) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article serviceRes = articleService.delete(id);

        return (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createList = articleService.createTransaction(dtos);

        return (createList != null)?
                ResponseEntity.status(HttpStatus.OK).body(createList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> pathch(@PathVariable Long id, @RequestBody ArticleForm dto) {
//        // 게시글의 ID, 수정할 내용 - 획득 완료
//        // 수정할 dto --> entity
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}", id, article.toString());
//
//        // db에 해당 인덱스 게시글 존재 확인: findById로 조회
//        Article dbData = articleRepository.findById(id).orElse(null);
//
//        if(dbData == null || id != article.getId()){ // 잘못된 요청인 경우의 처리
//            log.info("잘못된 요청, id: {}, article: {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        dbData.patch(article);
//        // patch는 수정사항만 업데이트하도록 처리하는 함수
//
//        // 만약 id가 잘못되었을 경우 처리
//        Article saveData = articleRepository.save(dbData);
//
//        return ResponseEntity.status(HttpStatus.OK).body(saveData);
//    }

//@DeleteMapping("/api/articles/{id}")
//public ResponseEntity<Article> delete(@PathVariable Long id) {
//    // 삭제할 데이터가 DB에 존재하는지 확인
//    Article dbData = articleRepository.findById(id).orElse(null);
//
//    // 만약 요청이 잘못되었을 경우의 처리
//    if (dbData == null) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//    }
//    // 삭제 완료 = 정상 삭제되었을 경우(Code = 200)
//    articleRepository.delete(dbData);
//    return ResponseEntity.status(HttpStatus.OK).body(null); // build() == body(null);
