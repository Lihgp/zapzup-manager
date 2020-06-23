package br.com.zapzup.manager.api.user.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class CreateUserRequest(
    @field:[NotEmpty]
    val name: String,
    @field:[NotEmpty]
    val username: String,
    @field:[NotEmpty]
    val email: String,
    @field:[NotEmpty]
    @field:[Min(8)]
    val password: String
)