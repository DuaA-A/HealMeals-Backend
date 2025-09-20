package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.model.User;

public class UserMapper {
    public static UserDTO toDTO(User u) {
        if (u == null) return null;
        return UserDTO.builder()
                .userId(u.getUserId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .gender(u.getGender())
                .dob(u.getDob())
                .address(u.getAddress())
                .phone(u.getPhone())
                .build();
    }
}
