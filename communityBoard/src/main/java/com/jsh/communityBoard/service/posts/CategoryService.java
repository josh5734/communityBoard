package com.jsh.communityBoard.service.posts;

import com.jsh.communityBoard.domain.category.CategoryDao;
import com.jsh.communityBoard.web.dto.CategorySaveRequestDto;
import com.jsh.communityBoard.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    // 등록
    @Transactional
    public Integer save(CategorySaveRequestDto requestDto){
        return categoryDao.save(requestDto.toEntity()).getId();
    }
}


