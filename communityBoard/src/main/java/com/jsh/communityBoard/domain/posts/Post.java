package com.jsh.communityBoard.domain.posts;


import com.jsh.communityBoard.domain.BaseTimeEntity;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.like.PostLike;
import com.jsh.communityBoard.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<PostLike> postLikes = new ArrayList<>();

    public void addPostLike(PostLike postLike){
        this.postLikes.add(postLike);
        postLike.setPost(this);
    }

    public Post createPost(User user, Category category, String title, String content){
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.postLikes = new ArrayList<>();
        this.user.getPostsList().add(this);
        this.category.getPostsList().add(this);
        return this;
    }

    // 게시물 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    // 게시물 조회 수 증가
    public void increaseViewCount(){
        this.viewCount++;
    }
}
