package com.jsh.dashboard;

import com.jsh.dashboard.web.dto.BaseResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

public class BaseResponseDtoTest {

    @Test
    public void lombokAppliedTest() {
        String name = "test";
        int amount = 10000;
        BaseResponseDto dto = new BaseResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

    @Test
    public void lombokAppliedTest2(){
        String nameBefore = "aaa";
        int amount = 10000;
        BaseResponseDto dto = new BaseResponseDto(nameBefore, amount);

        String nameAfter = "bbb";
        dto.setName(nameAfter);

        assertThat(dto.getName()).isEqualTo(nameAfter);

    }
}
