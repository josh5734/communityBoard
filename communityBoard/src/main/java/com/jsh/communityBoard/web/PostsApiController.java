package com.jsh.communityBoard.web;

import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.service.posts.PostsService;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import com.jsh.communityBoard.web.dto.PostsSaveRequestDto;
import com.jsh.communityBoard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    // 등록 API
    @PostMapping("/api/v1/posts")
    public ResponseEntity<Long> save(@RequestBody PostsSaveRequestDto requestsDto, @LoginUser SessionUser user){
        requestsDto.setUser(user.getNickname());
        Long id = postsService.save(requestsDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 수정 API
    @PutMapping("/api/posts/update/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        postsService.update(id, requestDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 전체 조회
    @GetMapping("/api/v1/posts/all")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAllDesc();
    }


    // 삭제 API
    @DeleteMapping("/api/posts/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        postsService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
