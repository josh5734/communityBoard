package com.jsh.communityBoard.config.auth;
import com.jsh.communityBoard.config.auth.dto.LoginUserDto;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.posts.Post;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public User login(LoginUserDto loginUser) throws Exception{
        String loginId = loginUser.getLoginId();
        String password = loginUser.getPassword();
        return userRepository.findByLoginId(loginId).filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("비밀번호나 아이디가 일치하지 않습니다."));
    }
}
