package com.qpbanking.identify.repository;

import com.qpbanking.identify.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);}
