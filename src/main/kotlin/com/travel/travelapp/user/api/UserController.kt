package com.travel.travelapp.user.api

import com.travel.travelapp.common.dto.ApiResponse
import com.travel.travelapp.security.AuthUser
import com.travel.travelapp.security.UserAuthorize
import com.travel.travelapp.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "로그인 후 사용할 수 있는 API")
@UserAuthorize
@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {
    @Operation(summary = "회원 정보 조회")
    @GetMapping
    fun getMemberInfo(user: AuthUser) =
        ApiResponse.success(userService.getMemberInfo(user.id))
}