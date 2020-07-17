package br.com.zapzup.manager.application.controller.auth

import br.com.zapzup.manager.api.auth.request.AuthTokenRequest
import br.com.zapzup.manager.domain.to.auth.AuthTokenTO

fun AuthTokenRequest.toDomain() = AuthTokenTO(
    email = this.email,
    password = this.password
)