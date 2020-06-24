package br.com.zapzup.manager.api.reset.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class GenerateTokenRequest(
    @field:[NotEmpty]
    @field:[Email]
    val email: String
)