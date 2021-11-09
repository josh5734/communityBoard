package com.jsh.communityBoard.config.auth;

import com.jsh.communityBoard.domain.user.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SecurityMember extends User {
    public SecurityMember(com.jsh.communityBoard.domain.user.User user) {
        super(user.getLoginId(), user.getPassword(), makeGrantedAuthority(user.getRole()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(Role role){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(role.name()));
        return list;
    }
}