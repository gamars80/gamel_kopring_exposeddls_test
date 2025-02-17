package com.example.gamel_kopring_expoest_test.service


import com.example.gamel_kopring_expoest_test.domain.auth.dto.LoginResponse
import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberUpdateRequest
import com.example.gamel_kopring_expoest_test.repository.MemberRepository
import com.example.gamel_kopring_expoest_test.util.JwtUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    suspend fun createMember(memberSaveReq: MemberSaveRequest): MemberDetail = withContext(Dispatchers.IO) {
        memberRepository.create(memberSaveReq)
    }

    suspend fun getMemberDetail(memberNo: Long): MemberDetail {
        return  memberRepository.getMemberDetail(memberNo) ?: throw Exception("Member not found")
    }

    suspend fun getMembers(page: Long, size: Int, userName: String?): PageResponse {
        return memberRepository.getMembers(page, size, userName)
    }

    @Transactional
    suspend fun update(memberNo: Long, req: MemberUpdateRequest) {
        memberRepository.update(memberNo, req);
    }

    @Transactional
    fun delete(memberNo: Long) {
        memberRepository.delete(memberNo)
    }

    fun login(loginId: String, password: String): LoginResponse {
        // 회원 정보 조회
        val member = memberRepository.findByLoginId(loginId)
            ?: throw IllegalArgumentException("Invalid credentials")

        // 실제 서비스에서는 암호화된 비밀번호 비교 (예: BCryptPasswordEncoder 사용)
        if (member.password != password) {
            throw IllegalArgumentException("Invalid credentials")
        }

        // JWT 토큰 생성
        val accessToken = JwtUtil.generateToken(member.memberNo, member.loginId, member.roleType);

        return LoginResponse.of(accessToken, member.memberNo, member.memberName, member.email);
    }
}