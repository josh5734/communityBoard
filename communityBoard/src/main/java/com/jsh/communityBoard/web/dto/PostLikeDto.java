package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.like.PostLike;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PostLikeDto {
    private Long id;
    private Long post_id;

    public PostLikeDto(PostLike postLike){
        this.id = postLike.getId();
        this.post_id = postLike.getPost().getId();
    }
}
