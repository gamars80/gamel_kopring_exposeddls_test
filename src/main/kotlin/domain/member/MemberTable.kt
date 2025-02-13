package domain.member

import domain.base.BaseTable

object MemberTable : BaseTable("member") {
    val memberNo = long("member_no").autoIncrement()  // 자동 증가 컬럼을 memberNo로 설정
    val loginId = varchar("loginId", 50).uniqueIndex()
    val password = varchar("password", 50)
    val memberName = varchar("member_name", 255)
    val email = varchar("email", 255)

    override val primaryKey = PrimaryKey(memberNo, name = "PK_MEMBER")   // memberNo를 기본키로 설정
}