package com.jsh.communityBoard.web;


import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.domain.comment.Comment;
import com.jsh.communityBoard.domain.like.PostLikeRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.CategoryService;
import com.jsh.communityBoard.service.posts.CommentService;
import com.jsh.communityBoard.service.posts.LikeService;
import com.jsh.communityBoard.service.posts.PostsService;
import com.jsh.communityBoard.web.dto.CommentResponseDto;
import com.jsh.communityBoard.web.dto.PostsListResponseDto;
import com.jsh.communityBoard.web.dto.PostsResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final CategoryService categoryService;
    private final LikeService likeService;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final CategoryRepository categoryRepository;
    private final CommentService commentService;


    // 홈화면
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        System.out.println("홈화면");
        List<PostsListResponseDto> postList = postsService.findAllDesc();
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
        System.out.println("getmapping");
        return "login";
    }


    // 마이페이지 화면
    @GetMapping("/mypage")
    public String mypage(Model model, @LoginUser SessionUser user){
        User u = userRepository.findById(user.getId()).get();

        // 내가 쓴 게시물
        model.addAttribute("postCount", u.getPostsList().size());

        // 좋아료를 누른 게시물
        model.addAttribute("likeCount", likeService.getLikedPostCount(user.getId()));

        // 댓글을 작성한 게시물
        model.addAttribute("commentCount", 0);

        if(user != null){
            model.addAttribute("userId", user.getId());
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
        model.addAttribute("categoryList", categoryService.findAll());
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("categoryName", dto.getCategoryName());
        model.addAttribute("post", dto);

        // 게시물의 댓글을 그룹순, 순서순으로 정렬
        Comparator<CommentResponseDto> orderComparator = Comparator.comparing(CommentResponseDto::getCommentOrder);
        List<CommentResponseDto> comments = dto.getCommentResponseDtoList();
        comments.sort(Comparator.comparing(CommentResponseDto::getParentCommentNum)
                .thenComparing(orderComparator));

        model.addAttribute("commentsCount", comments.size());
        model.addAttribute("comments", comments);

        // 유저가 이미 좋아요를 눌렀는지 전달
        model.addAttribute("liked", likeService.isAlreadyLike(user.getId(), id));

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
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("post", dto);
        model.addAttribute("categoryName", dto.getCategoryName());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts-update";
    }

    // 카테고리별 게시물들 보여주기
    @GetMapping("/api/v1/posts/category/{id}")
    public String categoryPosts(@PathVariable Integer id, Model model, @LoginUser SessionUser user){
        model.addAttribute("postList", postsService.findByCategory(id));
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("categoryName", categoryRepository.findById(id).get().getName());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts-by-category";
    }

    // 유저가 쓴 게시물 조회
    @GetMapping("/api/posts/userWritten/{userId}")
    public String findByUser(@PathVariable Long userId, Model model, @LoginUser SessionUser user){
        model.addAttribute("postList", postsService.findByUser(userId));
        model.addAttribute("categoryList", categoryService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "user-written-posts";
    }
    
    // 유저가 좋아요를 누른 게시물 조회
    @GetMapping("/api/posts/liked/{userId}")
    public String userLikedPosts(@PathVariable Long userId, Model model, @LoginUser SessionUser user){
        System.out.println("userId = " + userId);
        model.addAttribute("postList", likeService.findByUser(userId));
        model.addAttribute("categoryList", categoryService.findAll());

        
        List<PostsListResponseDto> dto = likeService.findByUser(userId);
        for(PostsListResponseDto x : dto){
            System.out.println("x.getId() = " + x.getId());
        }
        
        
        
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "user-liked-posts";
    }


}
