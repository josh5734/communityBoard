package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.comment.Comment;
import lombok.Data;

@Data
public class CommentResponseDto {
    private long id;
    private String content;
    private String writer;
    private long parentCommentNum;
    private long commentOrder;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getUser().getName();
        this.parentCommentNum = comment.getParentCommentNum();
        this.commentOrder = comment.getCommentOrder();
    }
}
