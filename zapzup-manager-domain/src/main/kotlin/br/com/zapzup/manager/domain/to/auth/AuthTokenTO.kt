package br.com.zapzup.manager.domain.to.auth

data class AuthTokenTO(
    val email: String,
    val password: String
)