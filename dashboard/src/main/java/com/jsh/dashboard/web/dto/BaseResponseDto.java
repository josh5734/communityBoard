package com.jsh.dashboard.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor    // fianl 필드가 포함된 생성자를 생성
public class BaseResponseDto {
    private final String name;
    private final int amount;

}
