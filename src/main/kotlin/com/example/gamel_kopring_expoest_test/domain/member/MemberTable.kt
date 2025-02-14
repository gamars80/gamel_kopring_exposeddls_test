package com.example.gamel_kopring_expoest_test.domain.member

import org.jetbrains.exposed.sql.Table

object MemberTable : Table("member") {

    val memberNo = long("member_no").autoIncrement()  // 자동 증가 컬럼
    val loginId = varchar("loginId", 50).uniqueIndex()
    val password = varchar("password", 50)
    val memberName = varchar("member_name", 255)
    val email = varchar("email", 255)

    override val primaryKey = PrimaryKey(memberNo, name = "PK_MEMBER")
}

