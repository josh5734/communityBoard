package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.comment.Comment;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class CommentDto {
    private Long post_id;
    private Post post;
    private User user;

    private String content;
    private long parentCommentNum;
    private long commentOrder;




    public Comment toEntity(){
        Comment comment = new Comment();
        return comment.createComment(user, post, content, parentCommentNum, commentOrder);
    }

}
