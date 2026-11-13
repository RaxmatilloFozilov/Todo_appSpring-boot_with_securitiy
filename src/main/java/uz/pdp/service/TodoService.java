package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dto.TodoRequest;
import uz.pdp.dto.TodoResponse;
import uz.pdp.entity.Todo;
import uz.pdp.entity.User;
import uz.pdp.exception.ResourceNotFoundException;
import uz.pdp.repository.TodoRepository;
import uz.pdp.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponse create(String username, TodoRequest req) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("not foutd"));

        Todo todo = Todo.builder()
                .title(req.title())
                .description(req.description())
                .completed(Boolean.TRUE.equals(req.completed()))
                .user(owner)
                .build();

        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getAll(String username) {
        return todoRepository.findAllByUserUsernameOrderByCreatedAtDesc(username)
                .stream().map(TodoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TodoResponse getOne(String username, Integer id) {
        return TodoResponse.from(findOwned(username, id));
    }

    @Transactional
    public TodoResponse update(String username, Integer id, TodoRequest req) {
        Todo todo = findOwned(username, id);
        todo.setTitle(req.title());
        todo.setDescription(req.description());
        if (req.completed() != null) {
            todo.setCompleted(req.completed());
        }
        return TodoResponse.from(todoRepository.save(todo));
    }

    @Transactional
    public void delete(String username, Integer id) {
        todoRepository.delete(findOwned(username, id));
    }

    private Todo findOwned(String username, Integer id) {
        return todoRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }
}
