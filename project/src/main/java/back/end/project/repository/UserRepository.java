package back.end.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import back.end.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
