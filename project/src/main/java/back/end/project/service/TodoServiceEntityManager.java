package back.end.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import back.end.project.entity.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TodoServiceEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Todo> getTodos(Long userId){
        return entityManager.createQuery("FROM Todo WHERE user.id = :userId", Todo.class)
        .setParameter("userId", userId).getResultList();
    }

    public Todo createTodo(Todo todo) {
        entityManager.persist(todo);
        return todo;
    }

    public void deleteTodo(Long id) {
        Todo todo = entityManager.find(Todo.class, id);
        if (todo != null) {
            entityManager.remove(todo);
        }
    }
    
}
