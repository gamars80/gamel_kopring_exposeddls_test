package com.example.gamel_kopring_expoest_test.repository


import com.example.gamel_kopring_expoest_test.config.DatabaseFactory
import com.example.gamel_kopring_expoest_test.domain.common.dto.PageResponse
import com.example.gamel_kopring_expoest_test.domain.member.LoginMember
import com.example.gamel_kopring_expoest_test.domain.member.Member
import com.example.gamel_kopring_expoest_test.domain.member.MemberDetail
import com.example.gamel_kopring_expoest_test.domain.member.MemberTable
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberSaveRequest
import com.example.gamel_kopring_expoest_test.domain.member.dto.MemberUpdateRequest
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import kotlinx.coroutines.Dispatchers

@Repository
class MemberRepository(
    private val databaseFactory: DatabaseFactory,
) {

    suspend fun create(memberSaveReq: MemberSaveRequest): MemberDetail = withContext(Dispatchers.IO) {
        transaction {
            val generatedId: Long =
                MemberTable.insert { row ->
                    row[loginId] = memberSaveReq.loginId
                    row[email] = memberSaveReq.email
                    row[password] = memberSaveReq.password
                    row[memberName] = memberSaveReq.memberName
                    row[roleType] = memberSaveReq.roleType
                }.resultedValues?.firstOrNull()?.get(MemberTable.memberNo)
                    ?: throw Exception("Member insertion failed: No ID generated")

            MemberDetail(
                memberNo = generatedId,
                memberName = memberSaveReq.memberName,
                email = memberSaveReq.email
            )
        }
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

    suspend fun update(memberNo: Long, req: MemberUpdateRequest) {
        databaseFactory.dbQuery {
            val updatedCount = MemberTable.update({ MemberTable.memberNo eq memberNo }) {
                req.memberName?.let { memberName -> it[MemberTable.memberName] = memberName }
            }

            if (updatedCount == 0) {
                throw Exception("Not Found Member", null)
            }
        }
    }

    fun delete(memberNo: Long) {
        MemberTable.deleteWhere { MemberTable.memberNo eq memberNo }
    }

    fun findByLoginId(loginId: String): LoginMember? {
        return transaction {
            MemberTable.select { MemberTable.loginId eq loginId }
                .map { row ->
                    LoginMember(
                        memberNo = row[MemberTable.memberNo],
                        loginId = row[MemberTable.loginId],
                        password = row[MemberTable.password],
                        memberName = row[MemberTable.memberName],
                        email = row[MemberTable.email],
                        roleType = row[MemberTable.roleType]
                    )
                }.singleOrNull()
        }
    }

}