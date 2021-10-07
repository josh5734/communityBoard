package com.jsh.communityBoard.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p inner join fetch p.user inner join fetch p.category JOIN FETCH p.postLikes ORDER BY p.id DESC ")
    List<Post> findAllDesc();

    @Query("SELECT p from Post p inner join fetch p.user inner join fetch p.category inner join fetch p.postLikes where p.user.id = :id")
    List<Post> findByUser(@Param(value = "id") Long user_id);


    @Query("SELECT p from Post p inner join fetch p.user inner join fetch p.category inner join fetch p.postLikes where p.category.id = :id")
    List<Post> findByCategory(@Param(value = "id") Integer category_id);
}
