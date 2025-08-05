package com.example.mytestproject.api;

import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // Read
    @GetMapping("articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스 전달
        List<CommentDto> dtos = commentService.comments(articleId);

        // 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos); // 예외 처리를 했다고 가정. 성공한 경우만 응답 전송
    }

    // Create
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에게 전달
        CommentDto createdto = commentService.create(articleId, dto);

        // 결과 return
        return ResponseEntity.status(HttpStatus.OK).body(createdto);
    }

    // Update
    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        // 서비스로 전달
        CommentDto updateDto = commentService.update(id, dto);


        // 결과 return
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // Delete
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        // 서비스로 전달
        CommentDto deleteDto = commentService.delete(id);

        // 결과 return
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);

    }

}