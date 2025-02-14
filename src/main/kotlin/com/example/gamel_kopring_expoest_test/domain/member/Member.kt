package com.example.gamel_kopring_expoest_test.domain.member

import org.jetbrains.exposed.sql.ResultRow

data class Member(
    val memberNo: Long,
    val memberName: String,
    val email: String
)

fun ResultRow.toMember(): Member {
    return Member(
        memberNo = this[MemberTable.memberNo],
        memberName = this[MemberTable.memberName],
        email = this[MemberTable.email]
    )
}

data class MemberDetail(
    val memberNo: Long? = null,
    val memberName: String,
    val email: String
)
