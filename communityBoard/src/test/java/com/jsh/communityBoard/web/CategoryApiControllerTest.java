package com.jsh.communityBoard.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryDao;
import com.jsh.communityBoard.domain.posts.Posts;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.web.dto.CategorySaveRequestDto;
import com.jsh.communityBoard.web.dto.PostsSaveRequestDto;
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
    private CategoryDao categoryDao;

    @AfterEach
    public void tearDown() throws Exception{
        categoryDao.deleteAll();
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
    @WithMockUser(roles="USER")
    public void Category_생성() throws Exception{
        // 데이터 전송 객체 생성
        String name = "category1";

        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder().name(name).build();
        // PostMapping URL
        String url = "http://localhost:" + port + "/api/category/add";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Category> all = categoryDao.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
    }


}
