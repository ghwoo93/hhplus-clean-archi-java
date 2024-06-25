package io.hhplus.clean.architect.lecture.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserVO {
    private final Long userId;
    private final String username;
}

