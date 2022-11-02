package rikkei.academy.service.user;

import org.springframework.stereotype.Service;
import rikkei.academy.model.User;

import java.util.Optional;


public interface IUserService {
    void save(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username); //Tìm kiếm username có tồn tại trong DB không?
}
