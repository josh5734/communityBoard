package com.jsh.dashboard.web;

import com.jsh.dashboard.web.dto.BaseResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/base")
    public String hello(){
        return "hello";
    }

    @GetMapping("/base/dto")
    public BaseResponseDto baseDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new BaseResponseDto(name, amount);
    }

}
