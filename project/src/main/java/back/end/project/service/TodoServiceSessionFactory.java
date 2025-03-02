package back.end.project.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import back.end.project.entity.Todo;

@Service
public class TodoServiceSessionFactory {
    
    @Autowired
    private SessionFactory sessionFactory;

    public List<Todo> getTodos(Long userId){
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Todo WHERE user.id = :userId", Todo.class)
        .setParameter("userId", userId).list();
    }

    public Todo createTodo(Todo todo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(todo);
        session.getTransaction().commit();
        session.close();
        return todo;
    }

    public void deleteTodo(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Todo todo = session.get(Todo.class, id);
        if (todo != null) {
            session.remove(todo);
        }
        session.getTransaction().commit();
        session.close();
    }
}
