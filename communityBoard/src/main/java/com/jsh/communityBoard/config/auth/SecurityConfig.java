package com.jsh.communityBoard.config.auth;

import com.jsh.communityBoard.service.posts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity // Activate Spring Security configurations.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2USerService;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable().csrf().disable()
                .headers().frameOptions().disable()
                .and()
//                http.
                .authorizeRequests() // URL별 권한 관리
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/login", "/signup").permitAll()
                    .antMatchers("/api/v1/**").authenticated()
                    .anyRequest().permitAll() // Give the permission accessing any other URL to "USER"
                .and()
                /*
                 * 커스텀 로그인 페이지로 리디렉션
                 */
                    .oauth2Login()
                    .loginPage("/login")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
//                .successHandler(new MyLoginSuccessHandler())
                .and()
                    .logout().logoutSuccessUrl("/login") // Redirect to "/login" when logout
                    .invalidateHttpSession(true)

                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2USerService);
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}



