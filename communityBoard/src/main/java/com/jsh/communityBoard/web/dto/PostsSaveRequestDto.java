package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.like.PostLike;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class PostsSaveRequestDto{
    private String userNickname;
    private User user;
    private Integer category_id;
    private Category category;
    private String title;
    private String content;
    private Integer viewCount;
    private List<PostLike> postLikes;


    public void setUser(String userNickname){
        this.userNickname = userNickname;
    }
    public void setCategory(Category category){
        this.category = category;
    }
    public void setUser(User user){
        this.user = user;
    }

    public Post toEntity(){
        Post post = new Post();
        return post.createPost(user, category, title, content);
    }
}
