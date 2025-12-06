package com.example.demo.domain.user.api;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;
import com.example.demo.domain.user.usecase.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User API", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserEndpoint  {

    private final UserUseCase userUseCase;

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다")
    @PostMapping
    public ResponseEntity<UserRes> createUser(@Valid @RequestBody UserCreateReq req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userUseCase.createUser(req));
    }

    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<UserRes> updateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id,
            @Valid @RequestBody UserUpdateReq req) {
        return ResponseEntity.ok(userUseCase.updateUser(id, req));
    }

    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<UserRes> getUserById(
            @Parameter(description = "사용자 ID") @PathVariable Long id) {
        return ResponseEntity.ok(userUseCase.getUserById(id));
    }

    @Operation(summary = "이메일로 사용자 조회", description = "이메일로 사용자를 조회합니다")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserRes> getUserByEmail(
            @Parameter(description = "이메일") @PathVariable String email) {
        return ResponseEntity.ok(userUseCase.getUserByEmail(email));
    }

    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<UserRes>> getAllUsers() {
        return ResponseEntity.ok(userUseCase.getAllUsers());
    }

    @Operation(summary = "활성 사용자 조회", description = "활성 상태인 사용자 목록을 조회합니다")
    @GetMapping("/active")
    public ResponseEntity<List<UserRes>> getActiveUsers() {
        return ResponseEntity.ok(userUseCase.getActiveUsers());
    }

    @Operation(summary = "이름으로 사용자 검색", description = "이름으로 사용자를 검색합니다")
    @GetMapping("/search")
    public ResponseEntity<List<UserRes>> searchUsersByName(
            @Parameter(description = "검색할 이름") @RequestParam String name) {
        return ResponseEntity.ok(userUseCase.searchUsersByName(name));
    }

    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사용자 활성화", description = "사용자를 활성화 상태로 변경합니다")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id) {
        userUseCase.activateUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 비활성화", description = "사용자를 비활성화 상태로 변경합니다")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id) {
        userUseCase.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
}
