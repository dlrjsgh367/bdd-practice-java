package com.example.demo.domain.user.infra.data.repository;

import com.example.demo.domain.user.infra.data.entity.QUserEntity;
import com.example.demo.domain.user.infra.data.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserEntity> findActiveUsers() {
        QUserEntity user = QUserEntity.userEntity;

        return queryFactory
                .selectFrom(user)
                .where(user.status.eq(1))
                .orderBy(user.createdAt.desc())
                .fetch();
    }

    @Override
    public List<UserEntity> searchByName(String name) {
        QUserEntity user = QUserEntity.userEntity;

        return queryFactory
                .selectFrom(user)
                .where(user.name.contains(name))
                .orderBy(user.name.asc())
                .fetch();
    }
}
