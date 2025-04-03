package com.project.physio_backend.Services.UserService;

import com.project.physio_backend.Entities.Users.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User updateUser(Long id, User userDetails);

    void deleteUser(Long id);

    boolean validatePassword(Long userId, String password); 
}
