package com.jsh.communityBoard.web;


import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.CommentService;
import com.jsh.communityBoard.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final CommentService commentService;

    // 등록 API
    @PostMapping("/api/comments")
    public Long save(@RequestBody CommentDto commentDto, @LoginUser SessionUser user){
        return commentService.save(commentDto, user);
    }
}
