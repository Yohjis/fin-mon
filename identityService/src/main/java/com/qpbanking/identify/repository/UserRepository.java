package com.qpbanking.identify.repository;

import com.qpbanking.identify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);}
