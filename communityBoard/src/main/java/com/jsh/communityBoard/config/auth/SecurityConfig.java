package com.jsh.communityBoard.config.auth;

import com.jsh.communityBoard.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity // Activate Spring Security configurations.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2USerService;
    private final CustomUserDetailsService customUserDetailsService;
    private DataSource datasource;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests() // URL별 권한 관리
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "**").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/api/v1/**").authenticated()
                    // Session Login 할때도 authentication이 되어야 함
                    .anyRequest().authenticated() // Give the permission accessing any other URL to "USER"
                .and()
                /*
                 * 커스텀 로그인 페이지로 리디렉션
                 */
                    .oauth2Login()
                    .loginPage("/login")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .successHandler(new MyLoginSuccessHandler())
                .and()
                    .logout().logoutSuccessUrl("/") // Redirect to "/" when logout
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2USerService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}


