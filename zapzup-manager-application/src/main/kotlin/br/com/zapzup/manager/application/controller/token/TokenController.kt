package br.com.zapzup.manager.application.controller.token

import br.com.zapzup.manager.api.token.TokenApi
import br.com.zapzup.manager.api.token.request.GenerateTokenRequest
import br.com.zapzup.manager.service.token.ITokenService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
    private val tokenService: ITokenService
) : TokenApi {

    override fun generateResetToken(@RequestBody @Validated generateTokenRequest: GenerateTokenRequest) {
        tokenService.generateToken(generateTokenRequest.toDomain(), "PASSWORD")
    }

    override fun validateToken(@RequestHeader token: String) {
        tokenService.validateToken(token)
    }
}