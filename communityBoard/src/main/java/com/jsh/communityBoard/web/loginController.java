package com.jsh.communityBoard.web;
import com.jsh.communityBoard.config.auth.LoginService;
import com.jsh.communityBoard.config.auth.dto.LoginUserDto;
import com.jsh.communityBoard.config.auth.dto.SessionUser;
import com.jsh.communityBoard.domain.message.Message;
import com.jsh.communityBoard.domain.message.StatusEnum;
import com.jsh.communityBoard.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@RequiredArgsConstructor
@Controller
public class loginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginUserDto loginUser, HttpServletRequest request){
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        boolean loginSuccess = true;
        try{
            User user = loginService.login(loginUser);
            HttpSession session = request.getSession();
            session.setAttribute("user", new SessionUser(user));
            message.setStatus(StatusEnum.OK);
        }
        catch (Exception e){
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage(e.getMessage());
                loginSuccess = false;
            }
        finally {
                return loginSuccess ? new ResponseEntity<>(message, headers, HttpStatus.OK)
                    : new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}