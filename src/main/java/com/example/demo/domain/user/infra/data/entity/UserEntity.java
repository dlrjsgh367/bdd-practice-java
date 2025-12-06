package com.example.demo.domain.user.infra.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("유저 키")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @Comment("유저 이름")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Comment("이메일")
    private String email;

    @Column(name = "phone", length = 20)
    @Comment("전화번호")
    private String phone;

    @Column(name = "status", nullable = false)
    @Comment("상태 (1: 활성, 0: 비활성)")
    private Integer status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Comment("생성일시")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Comment("수정일시")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
