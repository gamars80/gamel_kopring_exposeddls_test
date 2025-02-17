package com.example.gamel_kopring_expoest_test.domain.auth.dto

import com.example.gamel_kopring_expoest_test.enums.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthenticatedMember(
    val memberNo: Long,
    val roleType: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    // 실제 비밀번호를 관리하지 않는 경우 빈 문자열 반환
    override fun getPassword(): String = ""

    // memberNo를 문자열로 변환하여 username으로 사용
    override fun getUsername(): String = memberNo.toString()

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}