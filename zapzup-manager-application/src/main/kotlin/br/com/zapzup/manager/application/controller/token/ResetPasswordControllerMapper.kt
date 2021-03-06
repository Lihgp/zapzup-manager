package br.com.zapzup.manager.application.controller.token

import br.com.zapzup.manager.api.token.request.GenerateTokenRequest
import br.com.zapzup.manager.domain.to.token.GenerateTokenTO

fun GenerateTokenRequest.toDomain() = GenerateTokenTO(
    email = this.email
)