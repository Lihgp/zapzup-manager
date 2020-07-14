package br.com.zapzup.manager.domain.to.message

import java.time.OffsetDateTime

data class MessageTO(
    val id: String,
    val sender: String,
    val content: String,
    val createdAt: OffsetDateTime
)