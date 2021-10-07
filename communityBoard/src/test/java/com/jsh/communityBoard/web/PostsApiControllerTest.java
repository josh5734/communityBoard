package com.jsh.communityBoard.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.Role;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.PostsService;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import com.jsh.communityBoard.web.dto.PostsUpdateRequestDto;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // JPA 기능까지 함께 테스트할 수 있음

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostsService postsService;


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
    public void 게시물등록() throws Exception{
        User user = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        Category category = categoryRepository.save(new Category("자유게시판"));


        // 데이터 전송 객체 생성
        String title = "title1";
        String content = "post contents";

        postsRepository.save(new Post().createPost(user, category, title, content));

        List<Post> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void 유저아이디로게시물조회() throws Exception{
        User user = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        Long userId = user.getId();
        Category category = categoryRepository.save(new Category("자유게시판"));


        // 두 개의 포스트를 업로드
        postsRepository.save(new Post().createPost(user, category, "title1", "content1"));
        postsRepository.save(new Post().createPost(user, category, "title2", "content2"));

        String url = "http://localhost:" + port + "/api/v1/posts/user/" + userId;

        // getMapping
        mvc.perform(get(url))
                .andExpect(status().isOk());

        List<PostsListResponseDto> userPosts = postsService.findByUser(userId);
        assertThat(userPosts.size()).isEqualTo(2);
    }

    @Test
    public void 전체게시물조회() throws Exception{
        User user1 = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        User user2 = userRepository.save(User.builder().name("xy").email("abcdefg@naver.com").picture("no").role(Role.USER).build());
        Category category1 = categoryRepository.save(new Category("자유게시판"));
        Category category2 = categoryRepository.save(new Category("홍보게시판"));

        postsRepository.save(new Post().createPost(user1, category1, "title1", "content1"));
        postsRepository.save(new Post().createPost(user2, category2, "title2", "content2"));
        postsRepository.save(new Post().createPost(user2, category2, "title3", "content3"));

        String url = "http://localhost:" + port + "/api/v1/posts/all";

        // getMapping
        mvc.perform(get(url))
                .andExpect(status().isOk());

        List<PostsListResponseDto> all = postsService.findAllDesc();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    public void 카테고리로조회하기() throws Exception{
        User user1 = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        Category category1 = categoryRepository.save(new Category("자유게시판"));
        Category category2 = categoryRepository.save(new Category("홍보게시판"));

        postsRepository.save(new Post().createPost(user1, category1, "title1", "content1"));
        postsRepository.save(new Post().createPost(user1, category2, "title2", "content2"));
        postsRepository.save(new Post().createPost(user1, category1, "title3", "content3"));

        Integer targetCategoryId = category1.getId();
        String url = "http://localhost:" + port + "/api/v1/posts/category/" + targetCategoryId;

        // getMapping
        mvc.perform(get(url))
                .andExpect(status().isOk());

        List<PostsListResponseDto> targetCategoryPosts = postsService.findByCategory(targetCategoryId);
        assertThat(targetCategoryPosts.size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(roles="USER")
    public void 게시물수정하기() throws Exception{
        User user = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        Category category = categoryRepository.save(new Category("자유게시판"));
        Long postId = postsRepository.save(new Post().createPost(user, category, "title2", "content2")).getId();

        String updateTitle = "newTitle";
        String updateContent = "newContent";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + postId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);

    }

    @Test
    @WithMockUser(roles="USER")
    public void 게시물삭제하기() throws Exception{
        // 게시글 생성
        User user = userRepository.save(User.builder().name("sh").email("josh5734@naver.com").picture("no").role(Role.USER).build());
        Category category = categoryRepository.save(new Category("자유게시판"));
        Long postId = postsRepository.save(new Post().createPost(user, category, "title2", "content2")).getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + postId;

        // deleteMapping
        mvc.perform(delete(url))
                .andExpect(status().isOk());

        List<Post> all = postsRepository.findAll();
        assertThat(all).isEmpty();
    }
}
