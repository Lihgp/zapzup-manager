package br.com.zapzup.manager.application.controller.reset

import br.com.zapzup.manager.api.reset.request.GenerateTokenRequest
import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO

fun GenerateTokenRequest.toDomain() = GenerateTokenTO(
    email = this.email
)