package com.jsh.communityBoard.web;


import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.domain.like.PostLikeRepository;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.service.posts.CategoryService;
import com.jsh.communityBoard.service.posts.PostsService;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import com.jsh.communityBoard.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final CategoryService categoryService;
    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;

    // 홈화면
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        List<PostsListResponseDto> postList = postsService.findAllDesc();
        System.out.println("postList.size() = " + postList.size());
        model.addAttribute("postList", postList);
        model.addAttribute("categoryList", categoryService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // 마이페이지 화면
    @GetMapping("/mypage")
    public String mypage(Model model, @LoginUser SessionUser user){
        model.addAttribute("postList", postsService.findAllDesc());
        model.addAttribute("categoryList", categoryService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "mypage";
    }

    // 게시물 등록 화면
    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        model.addAttribute("categoryList", categoryService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
            model.addAttribute("user", user);
        }
        return "posts-save";
    }


    // 게시물 아이디로 개별 게시물 조회
    @GetMapping("/api/v1/posts/{id}")
    public String postDetail (@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        // 사이드바 목록
        model.addAttribute("categoryList", categoryService.findAll());

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("categoryName", dto.getCategoryName());
        model.addAttribute("post", dto);
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        // 게시글 조회수 증가
        postsService.increaseViewCount(id);
        return "post-detail";
    }

    // 게시물 수정 화면
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    // 카테고리별 게시물들 보여주기
    @GetMapping("/api/v1/posts/category/{id}")
    public String categoryPosts(@PathVariable Integer id, Model model, @LoginUser SessionUser user){
        model.addAttribute("postList", postsService.findByCategory(id));
        model.addAttribute("categoryList", categoryService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts-by-category";
    }
}
