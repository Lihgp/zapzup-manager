package br.com.zapzup.manager.domain.to.user

data class CreateUserTO (
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = ""
)
