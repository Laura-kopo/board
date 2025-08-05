package com.example.mytestproject.service;


import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository; // 댓글 관리 Repository

    @Autowired
    private ArticleRepository articleRepository; // 게시글 관리 Repository

    public List<CommentDto> comments(Long articleId) {
        // articleId로 게시글에 달린 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // Entity --> dto로 변환
//        List<CommentDto> dtos = new ArrayList<CommentDto>();

//        for(int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//        // 결과 return
//        return dtos;

        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment->CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());


    }

    @Transactional // 로직이 중간에 실패되었을 경우 Rollback 처리
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글이 존재하는지 확인
        // null이 될 수 있는 object(객체) = Optional 객체 - 값이 있으면 반환, 없으면 예외
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성에 실패하였습니다. 요청한 게시글이 존재하지 않습니다."));

        // 2. 댓글 DTO를 Entity로 변환
        Comment comment = Comment.createComment(dto, article);

        // 3. DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 수정하려는 대상이 DB에 있는지 조회
        Comment dbData = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정에 실패하였습니다. 요청하신 원본 댓글이 존재하지 않습니다."));

        // 2. 원본 데이터 수정
        dbData.patch(dto);

        // 3. DB에 수정 데이터 갱신
        Comment updateData = commentRepository.save(dbData);

        // 4. 변경된 데이터 entity --> dto 변환 후 반환
        return CommentDto.createCommentDto(updateData);

    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 댓글 조회
        Comment dbData = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패. 요청한 원본 댓글이 없습니다."));

        // 2. 댓글 삭제
        commentRepository.delete(dbData);

        // 3. dto로 변환 return
        return CommentDto.createCommentDto(dbData);
    }
}
