package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.category.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class CategorySaveRequestDto {
    private String name;

    @Builder
    public CategorySaveRequestDto(String name){
        this.name = name;
    }

    public Category toEntity(){
        return new Category(name);
    }
}
