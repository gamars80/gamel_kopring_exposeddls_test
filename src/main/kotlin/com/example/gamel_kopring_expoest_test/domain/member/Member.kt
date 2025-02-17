package com.example.gamel_kopring_expoest_test.domain.member

data class Member(
    val memberNo: Long,
    val memberName: String,
    val email: String
)

data class LoginMember(
    val memberNo: Long,
    val loginId: String,
    val password: String,
    val memberName: String,
    val email: String
)

data class MemberDetail(
    val memberNo: Long? = null,
    val memberName: String,
    val email: String
)
