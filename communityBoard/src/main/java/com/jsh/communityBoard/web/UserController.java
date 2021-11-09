package com.jsh.communityBoard.web;


import com.jsh.communityBoard.domain.user.UserRepository;
import com.jsh.communityBoard.service.posts.UserService;
import com.jsh.communityBoard.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;


    @PostMapping("/member")
    public String create(UserSaveRequestDto dto){
        userService.save(dto);
        return "redirect:/";
    }
}
