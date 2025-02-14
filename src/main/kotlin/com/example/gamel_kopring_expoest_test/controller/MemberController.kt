package com.example.gamel_kopring_expoest_test.controller

import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.common.dto.Pageable
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import com.example.gamel_kopring_expoest_test.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping()
    suspend fun createMember(@RequestBody memberSaveReq: MemberSaveRequest): ResponseEntity<MemberDetail> {
        val created = memberService.createMember(memberSaveReq)
        return ResponseEntity.ok(created)
    }

    @GetMapping("/{memberNo}")
    suspend fun getMemberDetail(@PathVariable memberNo: Long): Mono<MemberDetail>  {
        return memberService.getMemberDetail(memberNo)
    }

    @GetMapping("/list")
    suspend fun getMembers(paging: Pageable): ResponseEntity<PageResponse> {
        return ResponseEntity.ok(memberService.getMembers(paging.page, paging.size))
    }
}