package com.example.demo.domain.user.usecase;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;
import com.example.demo.domain.user.infra.UserInfra;
import com.example.demo.domain.user.model.User;
import com.example.demo.global.dto.resp.result.SingleResult;
import com.example.demo.global.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserUseCaseImpl implements UserUseCase {

    private final UserInfra userInfra;

    @Override
    @Transactional
    public SingleResult<Long> create(UserCreateReq req) {
        // 이메일 중복 체크
        if (userInfra.existsByEmail(req.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + req.getEmail());
        }
        User uer = User.create(req.getName(), req.getEmail(), req.getPhone());
        User savedUser = userInfra.save(uer);
        return ResponseUtil.getSingleResult(savedUser.getId());
    }

    @Override
    @Transactional
    public SingleResult<Long> update(Long id, UserUpdateReq req) {
        User user = userInfra.findById(id);
        User updatedUser = user.update(req.getName(), req.getPhone());
        User savedUser = userInfra.save(updatedUser);
        return ResponseUtil.getSingleResult(savedUser.getId());
    }

    @Override
    public SingleResult<UserRes> getUserById(Long id) {
        User user = userInfra.findById(id);
        return ResponseUtil.getSingleResult(UserRes.from(user));
    }

    @Override
    public SingleResult<UserRes> getUserByEmail(String email) {
        User user = userInfra.findByEmail(email);
        return ResponseUtil.getSingleResult(UserRes.from(user));
    }

    @Override
    public List<UserRes> getAllUsers() {
        List<User> users = userInfra.findAll();
        return users.stream()
                .map(UserRes::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRes> getActiveUsers() {
        List<User> users = userInfra.findActiveUsers();
        return users.stream()
                .map(UserRes::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRes> searchUsersByName(String name) {
        List<User> users = userInfra.searchByName(name);
        return users.stream()
                .map(UserRes::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SingleResult<Boolean> deleteUser(Long id) {
        userInfra.deleteById(id);
        return ResponseUtil.getSingleResult(true);
    }

    @Override
    @Transactional
    public SingleResult<Boolean> activateUser(Long id) {
        User user = userInfra.findById(id);
        User activatedUser = user.updateStatus(1);
        userInfra.save(activatedUser);
        return ResponseUtil.getSingleResult(true);
    }

    @Override
    @Transactional
    public SingleResult<Boolean> deactivateUser(Long id) {
        User user = userInfra.findById(id);
        User deactivatedUser = user.updateStatus(0);
        userInfra.save(deactivatedUser);
        return ResponseUtil.getSingleResult(true);
    }
}
