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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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

        // 부모 댓글은 부모 번호를 자신의 댓글 번호로 한다.
        if(commentDto.getParentCommentNum() == -1){
            commentDto.setParentCommentNum(commentDto.getCommentOrder());
            log.info("부모 댓글을 생성합니다. parentCommentNum = {}", commentDto.getCommentOrder());
        }else{
            long parentCommentNum = commentDto.getParentCommentNum();
            // 자식 댓글이라면, 부모 댓글의 자식 수를 증가시킨다.
            Comment parentComm = commentRepository.findByCommentOrder(parentCommentNum);
            parentComm.plusChildCommentCount();
            log.info("자식 댓글을 생성합니다. parentCommentNum = {}, parentChildCommentCount = {}", parentComm.getParentCommentNum(), parentComm.getChildCommentCount());
        }
        return commentRepository.save(commentDto.toEntity()).getId();
    }

    // 삭제
    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).get();
        // 자식 댓글을 삭제하면 부모 댓글의 자식 숫자를 감소시킨다.
        if (comment.getCommentOrder() != comment.getParentCommentNum()) {
            Comment parentComm = commentRepository.findByCommentOrder(comment.getParentCommentNum());
            parentComm.minusChildCommentCount();
        }
        commentRepository.delete(comment);
        log.info("자식 댓글을 삭제합니다.");
    }

    public void deletedUpdate(Long id){
        Comment comment = commentRepository.findById(id).get();
        comment.setDeleted();
    }
}
