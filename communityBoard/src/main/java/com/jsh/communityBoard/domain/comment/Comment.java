package com.jsh.communityBoard.domain.comment;


import com.jsh.communityBoard.domain.BaseTimeEntity;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 Id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post; // 댓글이 속한 게시물

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user; // 댓글을 작성한 유저

    private String content; // 댓글 내용

    private long parentCommentNum; // 자신이 속한 원댓글의 번호
    private long commentOrder; // 자신의 댓글 번호
    private long childCommentCount; // 자식 댓글의 개수

    private boolean isDeleted; // 댓글 삭제 여부

    private Integer likeCount; // 댓글 좋아요

    private Integer hateCount; // 댓글 싫어요

    public Comment createComment(User user, Post post, String content, long parentCommentNum, long commentOrder){
        this.user = user;
        this.post = post;
        this.content = content;
        this.likeCount = 0;
        this.hateCount = 0;
        this.childCommentCount = 0;
        this.isDeleted = false;
        this.parentCommentNum = parentCommentNum;
        this.commentOrder = commentOrder;
        this.user.getCommentList().add(this);
        this.post.getCommentList().add(this);
        return this;
    }

    // 댓글 수정
    public void update(String content){
        this.content = content;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public void setDeleted(){
        this.isDeleted = true;
    }
    public void plusChildCommentCount(){this.childCommentCount++;}
    public void minusChildCommentCount(){this.childCommentCount--;}

}
