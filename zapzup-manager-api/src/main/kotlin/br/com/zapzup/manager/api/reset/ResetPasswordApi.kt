package br.com.zapzup.manager.api.reset

import br.com.zapzup.manager.api.reset.request.GenerateTokenRequest
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "Password Token", tags = ["Password Token"], description = "Password Token Resources")
@RequestMapping(value = ["/reset-password"])
interface ResetPasswordApi {

    @PostMapping
    @ResponseStatus(ACCEPTED)
    @ResponseBody
    fun generateResetToken(@RequestBody @Validated generateTokenRequest: GenerateTokenRequest)

    @GetMapping(value = ["/validate"])
    @ResponseStatus(ACCEPTED)
    @ResponseBody
    fun validateToken(@RequestHeader token: String)
}