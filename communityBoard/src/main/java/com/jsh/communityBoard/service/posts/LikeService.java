package com.jsh.communityBoard.service.posts;

import com.jsh.communityBoard.domain.like.PostLike;
import com.jsh.communityBoard.domain.like.PostLikeRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.web.dto.PostLikeDto;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;

    // 좋아요 처리 서비스
    public boolean updateLike(User user, Long postId) {
        Post post = postsRepository.findById(postId).orElseThrow();

        //중복 좋아요 방지
        if(!isAlreadyLike(user, post)) {
            postLikeRepository.save(new PostLike(user, post));
            return true;
        }
        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크 - User, Post 객체로 찾기
    public boolean isAlreadyLike(User user, Post post) {
        return postLikeRepository.findByUserAndPost(user, post).isPresent();
    }

    //사용자가 이미 좋아요 한 게시물인지 체크 - User, Post 아이디로 찾기
    public boolean isAlreadyLike(Long user_id, Long post_id) {
        return postLikeRepository.findByUserIdAndPostId(user_id, post_id).isPresent();
    }

    // 유저가 좋아요를 누른 게시물 개수
    @Transactional
    public Integer getLikedPostCount(Long user_id){
        return postLikeRepository.findByUserId(user_id).size();
    }

    // 유저가 좋아요를 누른 게시물 조회
    @Transactional
    public List<PostsListResponseDto> findByUser(Long user_id) {
        return postsRepository.findByUserLike(user_id);
    }
}
