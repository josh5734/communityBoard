package com.jsh.communityBoard.config.auth.dto;


import com.jsh.communityBoard.domain.user.User;
import lombok.Getter;
import java.io.Serializable;

/*
 * 인증된 사용자 정보만 활용
 */
@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.id= user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
