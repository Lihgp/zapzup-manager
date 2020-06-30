package br.com.zapzup.manager.api.user.request

import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@ApiModel(value = "Update User Request")
data class UpdateUserRequest(
    @field:[NotNull]
    val username: String,
    @field:[NotNull]
    @field:[Email]
    val email: String,
    @field:[NotNull]
    val note: String
)