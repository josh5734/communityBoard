package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.category.Category;
import lombok.Data;

@Data
public class CategoryResponseDto {
    Integer id;
    String name;
    public CategoryResponseDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
