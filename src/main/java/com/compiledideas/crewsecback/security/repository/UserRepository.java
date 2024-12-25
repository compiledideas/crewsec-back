package com.compiledideas.crewsecback.security.repository;

import com.compiledideas.crewsecback.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDisplayName(String displayName);
    Optional<User> findByEmail(String username);
    Optional<User> findByPhone(String phone);
    Optional<User> findByPasswordReset(String passwordReset);
}
