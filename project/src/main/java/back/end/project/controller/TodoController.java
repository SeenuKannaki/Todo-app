package back.end.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import back.end.project.entity.Todo;
import back.end.project.service.TodoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/todos")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService; // Inject TodoService

    @GetMapping("/{userId}")
    public List<Todo> getTodos(@PathVariable Long userId) {
        logger.info("Fetching todos for user ID: {}", userId);
        return todoService.getTodos(userId); // Use TodoService
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo); // Use TodoService
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}