package br.com.zapzup.manager.api.chat.response

import br.com.zapzup.manager.api.user.response.UserResponse
import java.time.OffsetDateTime

data class ChatResponse(
    val id: String,
    val name: String,
    val description: String,
    val createdBy: String,
    val updatedBy: String,
    val deletedBy: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?,
    val deletedAt: OffsetDateTime?,
    val users: List<UserResponse>
)