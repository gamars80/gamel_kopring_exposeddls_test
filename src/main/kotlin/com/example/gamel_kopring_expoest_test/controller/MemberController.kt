package com.example.gamel_kopring_expoest_test.controller

import com.example.gamel_kopring_expoest_test.annotaion.AdminOnly
import com.example.gamel_kopring_expoest_test.annotaion.CurrentMemberNo
import com.example.gamel_kopring_expoest_test.annotaion.CustomerOnly
import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.common.dto.Pageable
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberUpdateRequest
import com.example.gamel_kopring_expoest_test.enums.RoleType
import com.example.gamel_kopring_expoest_test.service.MemberService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Member API", description = "회원 관리 관련 API")
@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signUp")
    suspend fun createMember(@RequestBody req: MemberSaveRequest): ResponseEntity<MemberDetail> {
        val created = memberService.createMember(req)
        return ResponseEntity.ok(created)
    }

    @CustomerOnly
    @GetMapping("/detail")
    suspend fun getMemberDetail(@CurrentMemberNo currentMemberNo: Long): MemberDetail {
        return memberService.getMemberDetail(currentMemberNo)
    }


    @AdminOnly
    @GetMapping("/list")
    suspend fun getMembers(
        paging: Pageable,
        @RequestParam("userName") userName: String?
    ): ResponseEntity<PageResponse> {
        return ResponseEntity.ok(memberService.getMembers(paging.page, paging.size, userName))
    }

    @PutMapping("/{memberNo}")
    suspend fun updateMember(@PathVariable memberNo: Long, @RequestBody req: MemberUpdateRequest) {
        memberService.update(memberNo, req)
    }

    @DeleteMapping("/{memberNo}")
    fun deleteMember(@PathVariable memberNo: Long) {
        return memberService.delete(memberNo)
    }
}