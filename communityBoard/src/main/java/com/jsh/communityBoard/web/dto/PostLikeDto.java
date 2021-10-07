package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.like.PostLike;

public class PostLikeDto {
    Long id;

    public PostLikeDto(PostLike postLike){
        this.id = postLike.getId();
    }
}
