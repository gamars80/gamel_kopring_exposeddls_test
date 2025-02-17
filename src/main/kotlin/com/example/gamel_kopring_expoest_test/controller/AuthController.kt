package com.example.gamel_kopring_expoest_test.controller

import com.example.gamel_kopring_expoest_test.domain.auth.dto.LoginRequest
import com.example.gamel_kopring_expoest_test.domain.auth.dto.LoginResponse
import com.example.gamel_kopring_expoest_test.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val memberService: MemberService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(memberService.login(loginRequest.loginId, loginRequest.password))
    }
}