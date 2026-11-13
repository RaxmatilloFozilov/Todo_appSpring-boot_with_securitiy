package uz.pdp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TodoRequest(
        @NotBlank(message = "error")
        @Size(max = 255)
        String title,

        @Size(max = 1000)
        String description,

        Boolean completed
) {
}
