package com.jsh.communityBoard.web;

import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.message.Message;
import com.jsh.communityBoard.domain.message.StatusEnum;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeApiController {
    private final LikeService likeService;
    private final UserRepository userRepository;

    // 좋아요 눌렀을 때
    @PostMapping("/like/{postId}")
    public ResponseEntity<Message> updateLike(@PathVariable Long postId, @LoginUser SessionUser user) {
        boolean result = false;
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (user != null) {
            User u = userRepository.findById(user.getId()).orElseThrow();
            result = likeService.updateLike(u, postId);
        }
        if(result){
            message.setStatus(StatusEnum.OK);
            message.setMessage("좋아요를 눌렀습니다.");
        }else{
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("이미 추천한 글입니다.");
        }
        return result ?
                new ResponseEntity<>(message, headers, HttpStatus.OK)
                : new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }
}
