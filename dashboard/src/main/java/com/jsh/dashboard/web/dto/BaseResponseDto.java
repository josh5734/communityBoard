package com.jsh.dashboard.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor    // fianl, NonNull 등의 필드가 포함된 생성자를 생성
public class BaseResponseDto {
    @NonNull private String name;
    @NonNull private int amount;
}
