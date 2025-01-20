package com.compiledideas.crewsecback.security.service;

import com.compiledideas.crewsecback.security.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Optional;

@Qualifier("user_service")
public interface UserService extends UserDetailsService {
    Page<User> findAll(int page, int size);
    User findById(Long id);
    User findByPasswordReset(String passwordReset);
    User addUser(User user);
    User findByEmail(String email);
    Optional<User> findByEmailOptional(String email);
    User updateUser(Long id, User user);
    User deleteUser(User user);
    User deleteUserById(Long userId);
}
