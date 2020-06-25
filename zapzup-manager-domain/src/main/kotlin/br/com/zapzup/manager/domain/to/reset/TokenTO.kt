package br.com.zapzup.manager.domain.to.reset

import java.time.OffsetDateTime

data class TokenTO(
    val id: String,
    val token: String,
    val expirationDate: OffsetDateTime
)