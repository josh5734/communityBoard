package com.jsh.communityBoard.service.posts;

import com.jsh.communityBoard.domain.like.PostLike;
import com.jsh.communityBoard.domain.like.PostLikeRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostLikeRepository likeRepository;
    private final PostsRepository postsRepository;

    public boolean addLike(User user, Long postId) {
        Post post = postsRepository.findById(postId).orElseThrow();

        //중복 좋아요 방지
        if(isNotAlreadyLike(user, post)) {
            likeRepository.save(new PostLike(user, post));
            return true;
        }
        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post).isEmpty();
    }
}
