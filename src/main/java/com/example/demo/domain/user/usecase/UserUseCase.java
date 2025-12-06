package com.example.demo.domain.user.usecase;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;

import com.example.demo.global.dto.resp.result.SingleResult;
import java.util.List;

public interface UserUseCase {

    SingleResult<Long> create(UserCreateReq req);
    SingleResult<Long> update(Long id, UserUpdateReq req);

    SingleResult<UserRes> getUserById(Long id);

    SingleResult<UserRes> getUserByEmail(String email);

    List<UserRes> getAllUsers();

    List<UserRes> getActiveUsers();

    List<UserRes> searchUsersByName(String name);

    SingleResult<Boolean> deleteUser(Long id);

    SingleResult<Boolean> activateUser(Long id);

    SingleResult<Boolean> deactivateUser(Long id);
}
