package com.qpbanking.identify.service;

import com.qpbanking.identify.model.User;
import org.apache.tomcat.util.security.Escape;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();
    User findByUserName(String username);


    User findById(Long id);
    void delete(Long id);

}
