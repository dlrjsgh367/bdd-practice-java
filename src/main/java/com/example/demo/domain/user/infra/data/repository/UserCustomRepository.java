package com.example.demo.domain.user.infra.data.repository;

import com.example.demo.domain.user.infra.data.entity.UserEntity;

import java.util.List;

public interface UserCustomRepository {

    List<UserEntity> findActiveUsers();

    List<UserEntity> searchByName(String name);
}
