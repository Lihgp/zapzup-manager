package br.com.zapzup.manager.api.auth

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.auth.request.AuthTokenRequest
import br.com.zapzup.manager.api.auth.response.AuthTokenResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "Authentication", tags = ["Authentication"], description = "Authentication Resources")
@RequestMapping(value = ["/auth"])
interface AuthApi {

    @PostMapping(value = ["/token"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "Generates a token for authentication")
    fun authenticate(@RequestBody @Validated authTokenRequest: AuthTokenRequest): ResponseWrapper<AuthTokenResponse>
}