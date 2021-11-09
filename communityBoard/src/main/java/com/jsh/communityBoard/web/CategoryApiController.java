package com.jsh.communityBoard.web;

import com.jsh.communityBoard.service.posts.CategoryService;
import com.jsh.communityBoard.web.dto.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;
}
