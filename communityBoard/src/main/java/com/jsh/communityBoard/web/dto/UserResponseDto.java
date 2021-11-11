package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.user.Role;
import com.jsh.communityBoard.domain.user.User;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserResponseDto {
    String name;
    String email;
    String picture;
    Role role;
    public UserResponseDto(User entity){
        this.name = entity.getNickname();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.role = entity.getRole();

    }
}
