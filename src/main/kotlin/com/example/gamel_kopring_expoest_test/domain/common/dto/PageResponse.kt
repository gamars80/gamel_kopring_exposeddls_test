package com.example.gamel_kopring_expoest_test.domain.common.dto

data class PageResponse (
    val page: Long = 1,
    val size: Int = 10,
    val totalCount: Long,
    val count: Int,
    val list: List<Any>? = null
)