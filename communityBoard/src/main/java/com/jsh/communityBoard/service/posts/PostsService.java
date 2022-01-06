package com.jsh.communityBoard.service.posts;


import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.category.CategoryRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;




    // 등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        Optional<User> user = userRepository.findByNickname(requestDto.getUserNickname());
        Optional<Category> category = categoryRepository.findById(requestDto.getCategory_id());
        if(user.isPresent()){
            requestDto.setUser(user.get());
        }
        if(category.isPresent()){
            requestDto.setCategory(category.get());
        }
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Post post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    // 삭제
    @Transactional
    public void delete(Long id){
        Post posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.getUser().getPostsList().removeIf(target -> target.equals(posts));    // 유저의 포스트 목록에서 해당 포스트 삭제
        posts.getCategory().getPostsList().removeIf(target -> target.equals(posts)); // 카테고리의 포스트 목록에서 해당 포스트 삭제

        postsRepository.delete(posts);
    }

    // 유저가 쓴 모든 게시물 조회
    @Transactional
    public List<PostsListResponseDto> findByUser(Long user_id) {
        return postsRepository.findByUser(user_id);
    }

    // 게시물 아이디로 조회
    public PostsResponseDto findById(Long id){
        Post entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity, entity.getPostLikes().size());
    }

    // 카테고리명으로 조회
    public List<PostsListResponseDto> findByCategory(Integer id){
        return postsRepository.findByCategory(id);
    }

    // 모든 포스트 조회
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        List<PostsListResponseDto> postsList = postsRepository.findAllDesc();
        for(PostsListResponseDto p : postsList){
            p.adjustCreatedTime();
        }
        return postsList;
    }


    // 조회 수 증가
    public void increaseViewCount(Long id) {
        Optional<Post> post = postsRepository.findById(id);
        if(post.isPresent()){
            post.get().increaseViewCount();
        }
        postsRepository.save(post.get());
    }

}
