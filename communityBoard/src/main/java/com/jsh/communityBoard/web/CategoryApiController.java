package com.jsh.communityBoard.web;

import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryDao;
import com.jsh.communityBoard.service.posts.CategoryService;
import com.jsh.communityBoard.service.posts.PostsService;
import com.jsh.communityBoard.web.dto.CategorySaveRequestDto;
import com.jsh.communityBoard.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    // 등록 API
    @PostMapping("/api/category/add")
    public Integer save(@RequestBody CategorySaveRequestDto requestsDto){
        return categoryService.save(requestsDto);
    }

}
