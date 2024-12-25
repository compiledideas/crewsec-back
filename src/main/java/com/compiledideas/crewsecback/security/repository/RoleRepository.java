package com.compiledideas.crewsecback.security.repository;

import com.compiledideas.crewsecback.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
