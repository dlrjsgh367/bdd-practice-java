package com.example.demo.domain.user.usecase;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;
import com.example.demo.domain.user.infra.UserInfra;
import com.example.demo.domain.user.model.User;
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
    public UserRes createUser(UserCreateReq req) {
        // 이메일 중복 체크
        if (userInfra.existsByEmail(req.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + req.getEmail());
        }

        // User 도메인 모델 생성
        User user = User.create(req.getName(), req.getEmail(), req.getPhone());

        // 저장
        User savedUser = userInfra.save(user);

        return UserRes.from(savedUser);
    }

    @Override
    @Transactional
    public UserRes updateUser(Long id, UserUpdateReq req) {
        // 사용자 조회
        User user = userInfra.findById(id);

        // 업데이트
        User updatedUser = user.update(req.getName(), req.getPhone());

        // 저장
        User savedUser = userInfra.save(updatedUser);

        return UserRes.from(savedUser);
    }

    @Override
    public UserRes getUserById(Long id) {
        User user = userInfra.findById(id);
        return UserRes.from(user);
    }

    @Override
    public UserRes getUserByEmail(String email) {
        User user = userInfra.findByEmail(email);
        return UserRes.from(user);
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
    public void deleteUser(Long id) {
        userInfra.deleteById(id);
    }

    @Override
    @Transactional
    public void activateUser(Long id) {
        User user = userInfra.findById(id);
        User activatedUser = user.updateStatus(1);
        userInfra.save(activatedUser);
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        User user = userInfra.findById(id);
        User deactivatedUser = user.updateStatus(0);
        userInfra.save(deactivatedUser);
    }
}
