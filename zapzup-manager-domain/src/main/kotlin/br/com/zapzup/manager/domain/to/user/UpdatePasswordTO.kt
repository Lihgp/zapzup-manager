package br.com.zapzup.manager.domain.to.user

data class UpdatePasswordTO(
    val oldPassword: String = "",
    val newPassword: String = ""
)