package br.com.zapzup.manager.api.user.response

import java.time.OffsetDateTime

class UserResponse(
    val id: String,
    val name: String,
    val note: String,
    val username: String,
    val email: String,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
)