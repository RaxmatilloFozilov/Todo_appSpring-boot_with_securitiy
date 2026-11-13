package uz.pdp.dto;

import uz.pdp.entity.Todo;

import java.time.LocalDateTime;

public record TodoResponse(
        Integer id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt
) {
    public static TodoResponse from(Todo t) {
        return new TodoResponse(t.getId(), t.getTitle(), t.getDescription(),
                t.isCompleted(), t.getCreatedAt());
    }
}
