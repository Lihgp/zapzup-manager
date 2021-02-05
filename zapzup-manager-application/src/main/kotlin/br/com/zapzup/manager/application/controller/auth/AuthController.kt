package br.com.zapzup.manager.application.controller.auth

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.auth.AuthApi
import br.com.zapzup.manager.api.auth.request.AuthTokenRequest
import br.com.zapzup.manager.api.auth.response.AuthTokenResponse
import br.com.zapzup.manager.service.auth.impl.AuthService
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) : AuthApi {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun authenticate(@RequestBody @Validated authTokenRequest: AuthTokenRequest):
        ResponseWrapper<AuthTokenResponse> {
        log.info("AuthTokenRequest: $authTokenRequest")

        val token = authService.generateToken(authTokenRequest.toDomain())

        return ResponseWrapper(AuthTokenResponse(token))
    }

}