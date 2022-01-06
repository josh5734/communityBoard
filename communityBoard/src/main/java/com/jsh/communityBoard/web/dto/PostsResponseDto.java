package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.posts.Post;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
public class PostsResponseDto {
    private Long id;
    private UserResponseDto user;
    private Integer categoryId;
    private String categoryName;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer postLikesCount;
    private List<CommentResponseDto> commentResponseDtoList;

    public PostsResponseDto(Post entity, int postLikeCount){
        this.id = entity.getId();
        this.user = new UserResponseDto(entity.getUser());
        this.categoryName = entity.getCategory().getName();
        this.categoryId = entity.getCategory().getId();
        this.title = entity.getTitle();
        this.content= entity.getContent();
        this.viewCount = entity.getViewCount();
        this.commentResponseDtoList = entity.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.postLikesCount = postLikeCount;
    }
}
