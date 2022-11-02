package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.model.User;

public interface IUserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
