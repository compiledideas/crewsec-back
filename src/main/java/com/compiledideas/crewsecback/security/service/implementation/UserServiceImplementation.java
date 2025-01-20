package com.compiledideas.crewsecback.security.service.implementation;

import com.compiledideas.crewsecback.exceptions.ResourceNotFoundException;
import com.compiledideas.crewsecback.security.models.User;
import com.compiledideas.crewsecback.security.repository.UserRepository;
import com.compiledideas.crewsecback.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service("user_service")
public class UserServiceImplementation implements UserService {

    private final UserRepository repository;

    @Override
    public Page<User> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("can't find user with id: " + id));
    }

    @Override
    public User findByPasswordReset(String passwordReset) {
        return repository.findByPasswordReset(passwordReset).orElseThrow(() -> new ResourceNotFoundException("can't find user with reset: " + passwordReset));
    }

    @Override
    public User addUser(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("can't find user with email: " + email));
    }

    @Override
    public Optional<User> findByEmailOptional(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User updateUser(Long id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    @Override
    public User deleteUser(User user) {
        repository.delete(user);
        return user;
    }

    @Override
    public User deleteUserById(Long userId) {
        User old = findById(userId);
        repository.deleteById(userId);
        return old;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("can't find user with email number +" + email ));
    }
}
