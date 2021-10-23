package com.jsh.communityBoard.domain.like;

import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.web.dto.PostLikeDto;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    //JPA 쿼리 메소드 네이밍 컨벤션
    Optional<PostLike> findByUserAndPost(User user, Post post);

    @Query(value = "select pl from PostLike pl where pl.user.id = :user_id and pl.post.id = :post_id")
    Optional<PostLike> findByUserIdAndPostId(@Param(value = "user_id") Long user_id, @Param(value = "post_id") Long post_id);


    @Query(value = "select pl from PostLike pl where pl.user.id = :id")
    List<PostLikeDto> findByUserId(@Param(value = "id") Long user_id);
}
