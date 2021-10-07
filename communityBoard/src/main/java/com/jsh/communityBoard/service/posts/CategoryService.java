package com.jsh.communityBoard.service.posts;

import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.web.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 모든 카테고리 조회
    // 모든 포스트 조회
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAll(){
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}


