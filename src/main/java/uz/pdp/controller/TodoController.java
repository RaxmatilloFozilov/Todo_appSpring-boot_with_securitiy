package uz.pdp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.TodoRequest;
import uz.pdp.dto.TodoResponse;
import uz.pdp.service.TodoService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(Principal principal, @Valid @RequestBody TodoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.create(principal.getName(), req));
    }

    @GetMapping
    public List<TodoResponse> getAll(Principal principal) {
        return todoService.getAll(principal.getName());
    }

    @GetMapping("/{id}")
    public TodoResponse getOne(Principal principal, @PathVariable Integer id) {
        return todoService.getOne(principal.getName(), id);
    }

    @PutMapping("/{id}")
    public TodoResponse update(Principal principal, @PathVariable Integer id, @Valid @RequestBody TodoRequest req) {
        return todoService.update(principal.getName(), id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Principal principal, @PathVariable Integer id) {
        todoService.delete(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
