package com.jsh.dashboard;


import com.jsh.dashboard.web.BaseController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

// manually Import
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BaseController.class)
public class BaseControllerTest {

    @Autowired
    private MockMvc mvc; // 컨트롤러의 API 테스트하는 용도인 MockMvc 객체 주입


    @Test
    @DisplayName("GetMapping:/base Test")
    public void test1() throws Exception{
        String hello = "hello";
        mvc.perform(get("/base")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

    @Test
    @DisplayName("GetMapping:/base/dto Test")
    public void test2() throws Exception{
        String name = "test";
        int amount = 10000;

        mvc.perform(
                get("/base/dto").param("name",name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
