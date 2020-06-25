package br.com.zapzup.manager.api.token

import br.com.zapzup.manager.api.token.request.GenerateTokenRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "Token", tags = ["Token"], description = "Token Resources")
@RequestMapping(value = ["/tokens"])
interface TokenApi {

    @PostMapping(value = ["/reset-password"])
    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ApiOperation(value = "Generates a token for password reset")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "Token sent"),
        ApiResponse(code = 404, message = "User not found")
    ])
    fun generateResetToken(@RequestBody @Validated generateTokenRequest: GenerateTokenRequest)

    @GetMapping(value = ["/validate"])
    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ApiOperation(value = "Validates the token")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "Token valid"),
        ApiResponse(code = 422, message = "Token expired")
    ])
    fun validateToken(@RequestHeader token: String)
}