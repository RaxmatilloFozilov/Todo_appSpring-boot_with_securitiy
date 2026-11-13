package uz.pdp.dto;

import uz.pdp.entity.Role;
import uz.pdp.entity.User;

public record UserResponse(Integer id, String username, String email, Role role) {

    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
    }
}
