package com.jsh.communityBoard.domain.user;

import com.jsh.communityBoard.domain.BaseTimeEntity;
import com.jsh.communityBoard.domain.comment.Comment;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.web.dto.UserSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor @Entity @Setter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column
    private String loginId;

    @Column
    private String password;

    private String name;

    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)    // For semantic information from DB, define the EnumType as String.
//    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> postsList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

//
    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User(UserSaveRequestDto dto){
        this.loginId = dto.getLoginId();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.role = Role.USER;
    }


    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
