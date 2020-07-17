package br.com.zapzup.manager.domain.to.chat

import br.com.zapzup.manager.domain.to.user.UserTO
import java.time.OffsetDateTime

data class ChatTO(
    val id: String,
    val name: String,
    val description: String,
    val createdBy: String,
    val updatedBy: String,
    val deletedBy: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?,
    val deletedAt: OffsetDateTime?,
    val lastMessageSentAt: OffsetDateTime?,
    val users: List<UserTO>
)