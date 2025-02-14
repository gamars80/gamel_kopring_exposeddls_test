package com.example.gamel_kopring_expoest_test.repository


import com.example.gamel_kopring_expoest_test.config.DatabaseFactory
import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.member.Member
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.MemberTable
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val databaseFactory: DatabaseFactory,
) {

    fun create(memberSaveReq: MemberSaveRequest): MemberDetail {
        val generatedId: Long = transaction {
            // insert 실행 후 결과 값(ResultRow 리스트)에서 첫 번째 행을 가져와 memberNo 컬럼의 값을 추출
            MemberTable.insert { row ->
                row[loginId] = memberSaveReq.loginId
                row[email] = memberSaveReq.email
                row[password] = memberSaveReq.password
                row[memberName] = memberSaveReq.memberName
            }.resultedValues?.firstOrNull()?.get(MemberTable.memberNo)
                ?: throw Exception("Member insertion failed: No ID generated")
        }

        return MemberDetail(
            memberNo = generatedId,
            memberName = memberSaveReq.memberName,
            email = memberSaveReq.email
        )
    }

    fun getMemberDetail(memberNo: Long): MemberDetail? {
        return transaction {
            MemberTable.select { MemberTable.memberNo eq memberNo }
                .map {
                    MemberDetail(it[MemberTable.memberNo], it[MemberTable.memberName], it[MemberTable.email])
                }
                .singleOrNull()
        }
    }

    suspend fun getMembers(page: Long, size: Int, userName: String?): PageResponse {
        val query = MemberTable.slice(
            MemberTable.memberNo,
            MemberTable.memberName,
            MemberTable.email
        ).selectAll()

        userName?.let { query.andWhere { MemberTable.memberName.like(it) } }
        return databaseFactory.dbQuery {
            val total = query.count()

            val members = query.limit(size, (page - 1) * size).map {
                Member(
                    memberName = it[MemberTable.memberName],
                    email = it[MemberTable.email],
                    memberNo = it[MemberTable.memberNo],
                )
            }

            PageResponse(
                page = page,
                size = size,
                totalCount = total,
                count = members.size,
                list = members
            )
        }
    }
}