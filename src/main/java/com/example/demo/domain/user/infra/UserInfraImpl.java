package com.example.demo.domain.user.infra;

import com.example.demo.domain.user.infra.data.entity.UserEntity;
import com.example.demo.domain.user.infra.data.repository.UserCustomRepository;
import com.example.demo.domain.user.infra.data.repository.UserJpaRepository;
import com.example.demo.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfraImpl implements UserInfra {

    private final UserJpaRepository userJpaRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInfraMapper userInfraMapper;

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = userInfraMapper.toUserEntity(user);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return userInfraMapper.toUser(savedEntity);
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userInfraMapper.toUser(userEntity);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userInfraMapper.toUser(userEntity);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userJpaRepository.findAll();
        return userInfraMapper.toUserList(userEntities);
    }

    @Override
    public List<User> findActiveUsers() {
        List<UserEntity> userEntities = userCustomRepository.findActiveUsers();
        return userInfraMapper.toUserList(userEntities);
    }

    @Override
    public List<User> searchByName(String name) {
        List<UserEntity> userEntities = userCustomRepository.searchByName(name);
        return userInfraMapper.toUserList(userEntities);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
}
