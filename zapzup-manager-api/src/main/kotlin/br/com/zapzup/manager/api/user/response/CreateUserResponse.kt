package br.com.zapzup.manager.api.user.response

import io.swagger.annotations.ApiModel
import java.time.OffsetDateTime

@ApiModel(value = "Create User Response")
data class CreateUserResponse(
    val id: String,
    val name: String,
    val note: String,
    val username: String,
    val email: String,
    val createdAt: OffsetDateTime
)