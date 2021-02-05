package br.com.zapzup.manager.api.auth.request

import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@ApiModel(value = "Authentication Token Request")
data class AuthTokenRequest(
    @field:[NotEmpty]
    @field:[Email]
    val email: String,
    @field:[NotEmpty]
    @field:[Size(min = 8)]
    val password: String
)