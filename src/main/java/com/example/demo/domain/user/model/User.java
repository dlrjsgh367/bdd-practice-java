package com.example.demo.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Factory method - 생성
    public static User create(String name, String email, String phone) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .status(1) // 1: 활성, 0: 비활성
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 업데이트
    public User update(String name, String phone) {
        return User.builder()
                .id(this.id)
                .name(name)
                .email(this.email)
                .phone(phone)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 상태 변경
    public User updateStatus(Integer status) {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .status(status)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
