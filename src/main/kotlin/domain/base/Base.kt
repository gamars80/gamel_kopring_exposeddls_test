package domain.base

data class Base(
    val createdAt: Long,
    val createdBy: Long,
    val lastModifiedAt: Long,
    val lastModifiedBy: Long
)