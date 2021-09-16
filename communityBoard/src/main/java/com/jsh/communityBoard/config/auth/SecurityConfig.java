package com.jsh.communityBoard.config.auth;

import com.jsh.communityBoard.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Activate Spring Security configurations.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2USerService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests() // URL별 권한 관리
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // Give the Permission to whose ROLE is "USER"
                .anyRequest().authenticated() // Give the permission accessing any other URL to "USER"
                .and()
                /*
                 * 커스텀 로그인 페이지로 리디렉션
                 */
                .oauth2Login()
                .loginPage("/login")
                .and()
                .logout().logoutSuccessUrl("/") // Redirect to "/" when logout
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2USerService);
    }
}


