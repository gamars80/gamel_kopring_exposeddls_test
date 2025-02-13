package domain.member

data class Member(
    val loginId: String,
    val password: String,
    val name: String,
    val email: String
)