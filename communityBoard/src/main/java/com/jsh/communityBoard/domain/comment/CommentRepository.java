package com.jsh.communityBoard.domain.comment;

import com.jsh.communityBoard.web.dto.CommentDto;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT coalesce(max(cmt.commentOrder), 0) FROM Comment cmt")
    Integer getMaximumCommentOrder();

    @Query("SELECT cmt FROM Comment cmt where cmt.commentOrder = :order")
    Comment findByCommentOrder(@Param(value = "order") Long commentOrder);


    @Query("SELECT distinct c.post.id from Comment as c where c.user.id = :user_id")
    List<Long> findByUserId(@Param(value = "user_id") Long user_id);
}