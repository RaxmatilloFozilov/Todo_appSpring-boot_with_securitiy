package uz.pdp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @Size(max = 100)
        String fullName,

        @NotBlank(message = "error")
        @Size(min = 3, max = 50)
        String username,

        @NotBlank(message = "error")
        String email,

        @Size(min = 6, max = 100)
        String password
) {
}