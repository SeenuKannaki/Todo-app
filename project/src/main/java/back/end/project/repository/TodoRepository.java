package back.end.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import back.end.project.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserId(Long userId);

    
}