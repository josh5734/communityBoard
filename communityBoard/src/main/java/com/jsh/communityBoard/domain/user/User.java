package com.jsh.communityBoard.domain.user;

import com.jsh.communityBoard.domain.BaseTimeEntity;
import com.jsh.communityBoard.domain.comment.Comment;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.web.dto.UserSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter @NoArgsConstructor @Entity @Setter
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    private String nickname;

    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)    // For semantic information from DB, define the EnumType as String.
//    @Column(nullable = false)
    private Role role;

    @Column(name = "auth")
    private String auth;


    @OneToMany(mappedBy = "user")
    private List<Post> postsList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

//
    @Builder
    public User(String nickname, String email, String picture, Role role){
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User(UserSaveRequestDto dto){
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.auth = "USER";
        this.role = Role.USER;
    }


    public User update(String nickname, String picture){
        this.nickname = nickname;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return username;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
