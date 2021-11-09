package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.category.Category;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.Role;
import com.jsh.communityBoard.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserSaveRequestDto {
    String loginId;
    String name;
    String password;
    String email;
    Role role;

    @Builder
    public UserSaveRequestDto(String loginId, String name, String password, String email){
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = Role.USER;
        System.out.println("this.role = " + this.role);
    }
}
