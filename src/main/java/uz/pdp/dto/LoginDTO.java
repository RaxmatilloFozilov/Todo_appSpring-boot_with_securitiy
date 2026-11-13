package uz.pdp.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        String username,

        String password
) {
}