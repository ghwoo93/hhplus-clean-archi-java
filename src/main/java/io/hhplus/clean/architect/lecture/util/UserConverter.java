package io.hhplus.clean.architect.lecture.util;



import io.hhplus.clean.architect.lecture.aggregate.dto.UserDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.User;
import io.hhplus.clean.architect.lecture.aggregate.vo.UserVO;

public class UserConverter {

    public static UserVO toVO(User user) {
        return new UserVO(user.userId(), user.username());
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.userId(), user.username());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getUserId(), userDTO.getUsername());
    }
}
