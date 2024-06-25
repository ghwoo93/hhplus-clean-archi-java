package io.hhplus.clean.architect.lecture.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserVO {
    private final Long userId;
    private final String username;
}

