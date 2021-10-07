package com.jsh.communityBoard.domain.like;

import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    //JPA 쿼리 메소드 네이밍 컨벤션
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
