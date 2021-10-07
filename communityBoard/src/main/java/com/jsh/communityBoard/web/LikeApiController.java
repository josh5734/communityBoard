package com.jsh.communityBoard.web;

import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeApiController {
    private final LikeService likeService;
    private final UserRepository userRepository;

    @PostMapping("/like/{postId}")
    public ResponseEntity<String> addLike(@LoginUser SessionUser user, @PathVariable Long postId) {
        boolean result = false;

        if (user != null) {
            User u = userRepository.findById(user.getId()).orElseThrow();
            result = likeService.addLike(u, postId);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
