package br.com.zapzup.manager.api.message.response

import java.time.OffsetDateTime

data class MessageResponse(
    val id: String,
    val sender: String,
    val content: String,
    val createdAt: OffsetDateTime
)