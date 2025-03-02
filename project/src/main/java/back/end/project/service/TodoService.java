package back.end.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import back.end.project.config.PersistenceConfig;

import back.end.project.entity.Todo;
import back.end.project.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoServiceEntityManager entityManagerService;

    @Autowired
    private TodoServiceSessionFactory sessionFactoryService;

    @Autowired
    private PersistenceConfig persistenceConfig;

    public List<Todo> getTodos(Long userId) {
        switch (persistenceConfig.getStrategy()) {
            case ENTITY_MANAGER:
                return entityManagerService.getTodos(userId);
            case SESSION_FACTORY:
                return sessionFactoryService.getTodos(userId);
            case JPA:
            default:
                return todoRepository.findByUserId(userId);
        }
    }

    public Todo createTodo(Todo todo) {
        switch (persistenceConfig.getStrategy()) {
            case ENTITY_MANAGER:
                return entityManagerService.createTodo(todo);
            case SESSION_FACTORY:
                return sessionFactoryService.createTodo(todo);
            case JPA:
            default:
                return todoRepository.save(todo);
        }
    }

    public void deleteTodo(Long id) {
        switch (persistenceConfig.getStrategy()) {
            case ENTITY_MANAGER:
                entityManagerService.deleteTodo(id);
                break;
            case SESSION_FACTORY:
                sessionFactoryService.deleteTodo(id);
                break;
            case JPA:
            default:
                todoRepository.deleteById(id);
                break;
        }
    }
}
