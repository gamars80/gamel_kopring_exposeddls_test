package com.example.gamel_kopring_expoest_test.domain.auth.dto

data class LoginResponse(
    val token: String,
    val memberNo: Long,
    val memberName: String,
    val email: String
){
    companion object {
        fun of(token: String, memberNo: Long, memberName: String, email: String): LoginResponse {
            return LoginResponse(token, memberNo, memberName, email)
        }
    }
}

