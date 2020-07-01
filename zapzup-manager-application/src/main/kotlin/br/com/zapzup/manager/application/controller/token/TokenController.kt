package br.com.zapzup.manager.application.controller.token

import br.com.zapzup.manager.api.token.TokenApi
import br.com.zapzup.manager.api.token.request.GenerateTokenRequest
import br.com.zapzup.manager.service.token.ITokenService
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
    private val tokenService: ITokenService
) : TokenApi {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun generateResetToken(@RequestBody @Validated generateTokenRequest: GenerateTokenRequest) {
        log.info("GenerateTokenRequest: $generateTokenRequest")

        tokenService.generateToken(generateTokenRequest.toDomain(), "PASSWORD")
    }

    override fun validateToken(@RequestHeader(value = "x-code") code: String) {
        log.info("Token code for validation: $code")

        tokenService.validateToken(code)
    }
}