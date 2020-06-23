package br.com.zapzup.manager.domain.to.user

import java.time.OffsetDateTime

data class UserTO(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val status: String = "",
    val email: String = "",
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
)