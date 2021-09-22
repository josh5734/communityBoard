package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {
    private String name;

    @Builder
    public CategorySaveRequestDto(String name){
        this.name = name;
    }

    public Category toEntity(){
        return Category.builder().name(name).build();
    }
}
