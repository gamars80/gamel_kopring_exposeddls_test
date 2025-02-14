package com.example.gamel_kopring_expoest_test.service


import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberUpdateRequest
import com.example.gamel_kopring_expoest_test.repository.MemberRepository
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

    suspend fun getMemberDetail(memberNo: Long): Mono<MemberDetail> {
        return Mono.fromCallable {
            memberRepository.getMemberDetail(memberNo) ?: throw Exception("Member not found")
        }.subscribeOn(/* scheduler = */ Schedulers.boundedElastic())
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
}