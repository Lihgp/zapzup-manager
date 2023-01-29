package br.com.zapzup.manager.api.auth.response

import io.swagger.annotations.ApiModel

@ApiModel(value = "Authentication Token Response")
data class AuthTokenResponse(
    val token: String
)