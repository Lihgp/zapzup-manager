package br.com.zapzup.manager.domain.to.token

import java.time.OffsetDateTime

data class TokenTO(
    val id: String,
    val code: String,
    val expirationDate: OffsetDateTime
)