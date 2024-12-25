package com.compiledideas.crewsecback.security.service;

import com.compiledideas.crewsecback.security.models.Role;
import com.compiledideas.crewsecback.security.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

@Qualifier("user_service")
public interface UserService extends UserDetailsService {
    Page<User> findAll(int page, int size);
    List<Role> findAllRoles();
    User findById(Long id);
    User findByPasswordReset(String passwordReset);
    User findByEmail(String email);
    User findByUsername(String username);
    User findByPhone(String phone);
    User addUser(User user);
    User addRoleToUser(Long roleId, Long userId);
    User updateUser(Long id, User user);
    User deleteUser(User user);
    User deleteUserById(Long userId);
}
