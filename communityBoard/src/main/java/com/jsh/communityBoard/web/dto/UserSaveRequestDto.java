package com.jsh.communityBoard.web.dto;

import com.jsh.communityBoard.domain.user.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserSaveRequestDto {
    String username;
    String nickname;
    String password;
    String email;

//    @Builder
//    public UserSaveRequestDto(String loginId, String name, String password, String email){
//        this.loginId = loginId;
//        this.name = name;
//        this.password = password;
//        this.email = email;
//        this.auth = "USER";
//        this.role = Role.USER;
//        System.out.println("this.role = " + this.role);
//    }
}
