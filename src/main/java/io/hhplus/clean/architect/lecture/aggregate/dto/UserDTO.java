package io.hhplus.clean.architect.lecture.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String username;
}
