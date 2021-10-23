package com.jsh.communityBoard.service.posts;


import com.jsh.communityBoard.config.auth.LoginUser;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.comment.Comment;
import com.jsh.communityBoard.domain.comment.CommentRepository;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.posts.PostsRepository;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.web.dto.CommentDto;
import com.jsh.communityBoard.web.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public Long save(CommentDto commentDto, @LoginUser SessionUser user) {
        Optional<User> u = userRepository.findByName(user.getName());
        Optional<Post> p = postsRepository.findById(commentDto.getPost_id());
        if(u.isPresent()) commentDto.setUser(u.get());
        if(p.isPresent()) commentDto.setPost(p.get());

        commentDto.setCommentOrder(commentRepository.getMaximumCommentOrder() + 1);
        System.out.println("commentDto.getParentCommentNum() = " + commentDto.getParentCommentNum());

        // 부모 댓글은 부모 번호를 자신의 댓글 번호로 한다.
        if(commentDto.getParentCommentNum() == -1){
            System.out.println("Parent Comment");
            commentDto.setParentCommentNum(commentDto.getCommentOrder());
            System.out.println("commentDto.getParentCommentNum() = " + commentDto.getParentCommentNum());
        }
        return commentRepository.save(commentDto.toEntity()).getId();
    }


}
