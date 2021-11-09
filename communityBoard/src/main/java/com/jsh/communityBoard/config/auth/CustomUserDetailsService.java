package com.jsh.communityBoard.config.auth;

import com.jsh.communityBoard.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        System.out.println("loadUserByUserName");
        return Optional.ofNullable(userRepository.findByLoginId(loginId))
                .filter(m -> m != null)
                .map(m -> new SecurityMember(m.get())).get();

    }

}
