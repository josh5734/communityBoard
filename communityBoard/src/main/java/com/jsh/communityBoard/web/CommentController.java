package com.jsh.communityBoard.web;
import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.service.posts.CommentService;
import com.jsh.communityBoard.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 등록
    @PostMapping("/api/comments")
    public ResponseEntity<Long> save(@RequestBody CommentDto commentDto, @LoginUser SessionUser user){
        Long createdId = commentService.save(commentDto, user);
        return new ResponseEntity<>(createdId, HttpStatus.OK);
    }

    // DB에서 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        commentService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 내용을 바꿈으로써 삭제
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<Long> deletedUpdate(@PathVariable Long id){
        commentService.deletedUpdate(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
