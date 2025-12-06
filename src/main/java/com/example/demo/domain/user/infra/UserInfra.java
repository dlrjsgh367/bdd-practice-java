package com.example.demo.domain.user.infra;

import com.example.demo.domain.user.model.User;

import java.util.List;

public interface UserInfra {

    User save(User user);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findAll();

    List<User> findActiveUsers();

    List<User> searchByName(String name);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
