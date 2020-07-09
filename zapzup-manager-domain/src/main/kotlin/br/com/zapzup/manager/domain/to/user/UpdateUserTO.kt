package br.com.zapzup.manager.domain.to.user

data class UpdateUserTO (
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val note: String = ""
)
