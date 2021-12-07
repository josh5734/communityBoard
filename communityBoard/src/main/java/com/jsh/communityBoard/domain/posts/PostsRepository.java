package com.jsh.communityBoard.domain.posts;

import com.jsh.communityBoard.web.dto.CommentResponseDto;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import com.jsh.communityBoard.web.dto.PostsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p FROM Post p inner join fetch p.user inner join fetch p.category LEFT JOIN FETCH p.postLikes")
    List<PostsListResponseDto> findAllDesc();

    @Query(value = "select p FROM Post p inner join fetch p.user inner join fetch p.category LEFT JOIN FETCH p.postLikes where p.user.id = :id")
    List<PostsListResponseDto> findByUser(@Param(value = "id") Long user_id);

    @Query(value = "select p FROM Post p inner join fetch p.user inner join fetch p.category LEFT JOIN FETCH p.postLikes where p.category.id = :id")
    List<PostsListResponseDto> findByCategory(@Param(value = "id") Integer category_id);

    @Query(value = "select p FROM Post p inner join fetch p.user inner join fetch p.category LEFT JOIN FETCH p.postLikes where p.id = :id")
    PostsListResponseDto findByPostId(@Param(value = "id") Long post_id);

    @Query(value = "select p FROM Post p inner join fetch p.user inner join fetch p.category LEFT JOIN FETCH p.postLikes as pl where pl.user.id = :id")
    List<PostsListResponseDto> findByUserLike(@Param(value = "id") Long user_id);
}
