package com.example.gamel_kopring_expoest_test.domain.member

data class Members(
    val totalCount: Long,
    val count: Int,
    val cursor: Long,
    val size: Int,
    val members: List<Member>
)

