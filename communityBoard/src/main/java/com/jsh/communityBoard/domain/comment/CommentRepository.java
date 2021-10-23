package com.jsh.communityBoard.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT coalesce(max(cmt.commentOrder), 0) FROM Comment cmt")
    Integer getMaximumCommentOrder();

}