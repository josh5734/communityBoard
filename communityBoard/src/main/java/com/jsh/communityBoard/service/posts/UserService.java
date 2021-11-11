package com.jsh.communityBoard.service.posts;

import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.user.User;
import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

//@RequiredArgsConstructor
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Transactional
//    public Long save(UserSaveRequestDto requestDto){
//        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
//        User user = new User(requestDto);
//        return userRepository.save(user).getId();
//    }
//}

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;       // 세션 관리

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((username)));
        httpSession.setAttribute("user", new SessionUser(user));
        return user;
    }

    public Long save(UserSaveRequestDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));
        return userRepository.save(new User(dto)).getId();
    }
}