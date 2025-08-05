package com.example.mytestproject.controller;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
//    private static final Logger log = LogManager.getLogger(ArticleController.class);
    // 의존성 주입 = DI(Dependency Injection)
    @Autowired // 이미 생선한 repo 객체를 DI
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new") //GetMapping은 url주소로 연결
    public String newarticleForm(){
        return "articles/new_form";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
//        System.out.println(form.toString());
        log.info(form.toString());

        // DTO --> ENTITY 변환
        Article article = form.toEntity(); // 실행하면 변환이 된다고 가정
//        System.out.println(article.toString());
        log.info(article.toString());

        // Repo로 Entity를 DB 저장
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
        // redirect를 사용하는 이유는 동적으로 경로가 변하는 경우에 사용함
        // a는 경로가 정적인 경우에 사용함
    }

    @GetMapping("/articles/{id}")
    public String articleId(@PathVariable Long id, Model model) { //URL로 전달된 변수값을 파라미터로 받겠다는 에너테이션
        
        log.info("id = " + id);

        //1. ID로 DB 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 데이터 등록
        model.addAttribute("data", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 사용자에게 화면 전달
        return "articles/articlesId";
    }

    @GetMapping("/articles/index")
    public String index(Model model) {
        // 1. DB에 모든 게시글 데이터 가져오기
        //Iterable<Article> articleList = articleRepository.findAll(); // 리턴형은 엔티티. 업캐스팅
        //List<Article> articleList2 = (List<Article>)articleRepository.findAll(); // 다운캐스팅
        List<Article> articleList = articleRepository.findAll();

        // 2. 등록하기
        model.addAttribute("articleList", articleList);

        // 3. 화면 리턴
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    // 내용물이 있으면 PostMapping, 없으면 GetMapping 사용
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터 양식 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 수정한 데이터 등록하기
        model.addAttribute("articleUpdate", articleEntity);

        // 3. 뷰 페이지 설정
        return "articles/updateForm";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        // 1. DTO --> Entity 변환
        Article articleEntity = form.toEntity(); // 새롭게 받은 수정된 데이터

        // 2. Entity를 DB에 저장하기
        // 2-1. 수정하기 전 데이터 불러오기
        // DB에서 데이터 찾기
        Article dbData = articleRepository.findById(articleEntity.getId()).orElse(null);
        log.info(dbData.toString());
        
        // 2-2
        // 갱신데이터를 저장하고
        // 데이터가 없는 경우 처리
        if (dbData != null) {
            Article saved = articleRepository.save(articleEntity);
            // articleEntity에 담긴 데이터가 수정된 데이터임
            log.info(saved.toString());
        }

        // 3. 수정 결과 화면 전환
        return "redirect:/articles/" + articleEntity.getId();
    }

    // HTTP 메서드 = GET, POST, UPDATE, DELETE
    // HTML 메서드 = GET, POST

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청 확인");

        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());

        if(article != null) {
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        // delete를 한 후에 return을 받지 않는 이유는 delete의 return이 void이기 때문

        return "redirect:/articles/index";
    }
}
