package com.example.demo.domain.user.api;

import com.example.demo.domain.user.api.dto.req.UserCreateReq;
import com.example.demo.domain.user.api.dto.req.UserUpdateReq;
import com.example.demo.domain.user.api.dto.res.UserRes;
import com.example.demo.domain.user.usecase.UserUseCase;
import com.example.demo.global.dto.resp.SuccessResponse;
import com.example.demo.global.dto.resp.result.SingleResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserEndpoint  {

    private final UserUseCase userUseCase;

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다")
    @PostMapping
    public SuccessResponse<SingleResult<Long>> create(
        @RequestBody @Valid UserCreateReq req
    ) {
        return SuccessResponse.ok(userUseCase.create(req));
    }

    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다")
    @PutMapping("/{id}")
    public SuccessResponse<SingleResult<Long>> update(
        @PathVariable Long id,
        @RequestBody @Valid UserUpdateReq req
    ) {
        return SuccessResponse.ok(userUseCase.update(id, req));
    }

    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다")
    @GetMapping("/{id}")
    public SuccessResponse<SingleResult<UserRes>> getUserById(
            @Parameter(description = "사용자 ID") @PathVariable Long id
    ) {
        return SuccessResponse.ok(userUseCase.getUserById(id));
    }

    @Operation(summary = "이메일로 사용자 조회", description = "이메일로 사용자를 조회합니다")
    @GetMapping("/email/{email}")
    public SuccessResponse<SingleResult<UserRes>> getUserByEmail(
            @Parameter(description = "이메일") @PathVariable String email
    ) {
        return SuccessResponse.ok(userUseCase.getUserByEmail(email));
    }

    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 목록을 조회합니다")
    @GetMapping
    public SuccessResponse<SingleResult<List<UserRes>>> getAllUsers() {
        return SuccessResponse.ok(SingleResult.of(userUseCase.getAllUsers()));
    }

    @Operation(summary = "활성 사용자 조회", description = "활성 상태인 사용자 목록을 조회합니다")
    @GetMapping("/active")
    public SuccessResponse<SingleResult<List<UserRes>>> getActiveUsers()
    {
        return SuccessResponse.ok(SingleResult.of(userUseCase.getActiveUsers()));
    }

    @Operation(summary = "이름으로 사용자 검색", description = "이름으로 사용자를 검색합니다")
    @GetMapping("/search")
    public SuccessResponse<SingleResult<List<UserRes>>> searchUsersByName(
            @Parameter(description = "검색할 이름") @RequestParam String name)
    {
        return SuccessResponse.ok(SingleResult.of(userUseCase.searchUsersByName(name)));
    }

    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다")
    @DeleteMapping("/{id}")
    public SuccessResponse<SingleResult<Boolean>> deleteUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id
    ) {
        return SuccessResponse.ok(userUseCase.deleteUser(id));
    }

    @Operation(summary = "사용자 활성화", description = "사용자를 활성화 상태로 변경합니다")
    @PatchMapping("/{id}/activate")
    public SuccessResponse<SingleResult<Boolean>> activateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id
    ) {
        return SuccessResponse.ok(userUseCase.activateUser(id));
    }

    @Operation(summary = "사용자 비활성화", description = "사용자를 비활성화 상태로 변경합니다")
    @PatchMapping("/{id}/deactivate")
    public SuccessResponse<SingleResult<Boolean>> deactivateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long id
    ) {
        return SuccessResponse.ok(userUseCase.deactivateUser(id));
    }
}
