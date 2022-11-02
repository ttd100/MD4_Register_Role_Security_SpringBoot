package rikkei.academy.service.user;

import org.springframework.stereotype.Service;
import rikkei.academy.model.User;


public interface IUserService {
    void save(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
