package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.like.PostLike;
import com.jsh.communityBoard.domain.posts.Post;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
public class PostsListResponseDto {
    private Long id;
    private UserResponseDto user;
    private CategoryResponseDto category;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer postLikesCount;
    private List<PostLikeDto> postLikes;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Post entity){
        this.id = entity.getId();
        this.user = new UserResponseDto(entity.getUser());
        this.category = new CategoryResponseDto(entity.getCategory());
        this.title = entity.getTitle();
        this.content= entity.getContent();
        this.viewCount = entity.getViewCount();
        this.postLikes = entity.getPostLikes().stream().map(PostLikeDto::new).collect(Collectors.toList());
        this.postLikesCount = postLikes.size();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
