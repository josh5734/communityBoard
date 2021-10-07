package com.jsh.communityBoard.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.service.posts.CategoryService;
import com.jsh.communityBoard.web.dto.CategoryResponseDto;
import com.jsh.communityBoard.web.dto.CategorySaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // JPA 기능까지 함께 테스트할 수 있음

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryService categoryService;

    @AfterEach
    public void tearDown() throws Exception{
        categoryRepository.deleteAll();
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void 모든카테고리조회(){
        List<CategoryResponseDto> categoryLists = categoryService.findAll();
        for(CategoryResponseDto category: categoryLists){
            System.out.println("category.id = " + category.getId() + " category.name = " + category.getName());
        }
    }


}
