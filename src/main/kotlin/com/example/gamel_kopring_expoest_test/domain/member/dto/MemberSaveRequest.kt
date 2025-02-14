package com.example.gamel_kopring_expoest_test.domain.member.dto


data class MemberSaveRequest(
    val memberNo: Long? = null,
    val loginId: String,
    val password: String,
    val memberName: String,
    val email: String
) {
    init {
        require(loginId.isNotBlank()) { "loginId는 빈 값일 수 없습니다." }
        require(password.isNotBlank()) { "password는 빈 값일 수 없습니다." }
        require(memberName.isNotBlank()) { "memberName은 빈 값일 수 없습니다." }
        require(email.isNotBlank()) { "email은 빈 값일 수 없습니다." }
        require(email.matches(EMAIL_REGEX)) { "email 형식이 올바르지 않습니다." }
    }

    companion object {
        // 간단한 이메일 정규식. 필요에 따라 조정 가능
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}

