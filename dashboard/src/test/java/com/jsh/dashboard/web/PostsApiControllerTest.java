package com.jsh.dashboard.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.dashboard.domain.posts.Posts;
import com.jsh.dashboard.domain.posts.PostsRepository;
import com.jsh.dashboard.web.dto.PostsSaveRequestDto;
import com.jsh.dashboard.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // JPA 기능까지 함께 테스트할 수 있음

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
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
    public void Posts_등록() throws Exception{
        // 데이터 전송 객체 생성
        String title = "abcd";
        String content = "asdfsadfasdfsaf";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        // PostMapping URL
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());



        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception{
        // 게시글 생성
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String updateTitle = "newTitle";
        String updateContent = "newContent";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);

    }

    /**
    @Test
    @WithMockUser(roles="USER")
    public void Posts_삭제() throws Exception{
        // 게시글 생성
        Posts posts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        Long postsId = posts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + postsId;
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts);

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestEntity)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all).isEmpty();
    }
    */
}
