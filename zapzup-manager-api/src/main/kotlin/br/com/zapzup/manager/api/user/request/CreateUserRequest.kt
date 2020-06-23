package br.com.zapzup.manager.api.user.request

import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@ApiModel(value = "Create User Request")
data class CreateUserRequest(
    @field:[NotEmpty]
    val name: String,
    @field:[NotEmpty]
    val username: String,
    @field:[NotEmpty]
    @field:[Email]
    val email: String,
    @field:[NotEmpty]
    @field:[Size(min = 8)]
    val password: String
)