package com.example.demo.domain.user.usecase;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;

import java.util.List;

public interface UserUseCase {

    UserRes createUser(UserCreateReq req);

    UserRes updateUser(Long id, UserUpdateReq req);

    UserRes getUserById(Long id);

    UserRes getUserByEmail(String email);

    List<UserRes> getAllUsers();

    List<UserRes> getActiveUsers();

    List<UserRes> searchUsersByName(String name);

    void deleteUser(Long id);

    void activateUser(Long id);

    void deactivateUser(Long id);
}
