package com.example.gamel_kopring_expoest_test.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.example.gamel_kopring_expoest_test.domain.member.MemberTable
import com.example.gamel_kopring_expoest_test.domain.post.PostsTable
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseFactory {

    @PostConstruct
    fun init() {
        // HikariCP 데이터소스 설정
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3305/kopring_test?serverTimezone=UTC&useSSL=false"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = "root"
            password = "1234"
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        // 테이블이 없으면 생성
        // 상용에서는 하면 안될듯
        transaction {
            SchemaUtils.create(MemberTable, PostsTable)
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}