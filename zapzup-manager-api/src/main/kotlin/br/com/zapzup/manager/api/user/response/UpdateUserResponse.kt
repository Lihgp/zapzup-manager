package br.com.zapzup.manager.api.user.response

import io.swagger.annotations.ApiModel
import java.time.OffsetDateTime

@ApiModel(value = "Update User Response")
data class UpdateUserResponse(
    val id: String,
    val note: String,
    val username: String,
    val email: String,
    val updatedAt: OffsetDateTime
)