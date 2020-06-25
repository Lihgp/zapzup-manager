package br.com.zapzup.manager.domain.to.reset

import java.time.OffsetDateTime

data class ResetPasswordTokenTO(
    val id: String,
    val token: String,
    val expirationDate: OffsetDateTime
)