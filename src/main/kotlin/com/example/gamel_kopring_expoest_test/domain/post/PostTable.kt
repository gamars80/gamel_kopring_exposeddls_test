package com.example.gamel_kopring_expoest_test.domain.post

import com.example.gamel_kopring_expoest_test.domain.member.MemberTable
import org.jetbrains.exposed.sql.Table


object PostsTable : Table("post") {
    val postNo = long("post_no").autoIncrement()
    val title = varchar("title", 255)
    val content = text("content")
    // 외래키 참조 시, 참조할 컬럼(MemberTable.memberNo)을 명시적으로 전달합니다.
    val memberNo = reference("member_no", MemberTable.memberNo)

    override val primaryKey = PrimaryKey(postNo, name = "PK_POST")   // postNo 기본키로 설정
}