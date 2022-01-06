package com.jsh.communityBoard.web;


import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.UserService;
import com.jsh.communityBoard.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserSaveRequestDto dto){
        userService.save(dto);

        return "redirect:/";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
