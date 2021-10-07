package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.posts.Post;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PostsResponseDto {
    private Long id;
    private UserResponseDto user;
    private String categoryName;
    private String title;
    private String content;
    private Integer viewCount;


    public PostsResponseDto(Post entity){
        this.id = entity.getId();
        this.user = new UserResponseDto(entity.getUser());
        this.categoryName = entity.getCategory().getName();
        this.title = entity.getTitle();
        this.content= entity.getContent();
        this.viewCount = entity.getViewCount();
    }
}
