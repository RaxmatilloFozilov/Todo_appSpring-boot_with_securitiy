package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findAllByUserUsernameOrderByCreatedAtDesc(String username);

    Optional<Todo> findByIdAndUserUsername(Integer id, String username);
}
