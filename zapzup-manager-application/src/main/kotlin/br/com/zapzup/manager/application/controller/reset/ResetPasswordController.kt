package br.com.zapzup.manager.application.controller.reset

import br.com.zapzup.manager.api.reset.ResetPasswordApi
import br.com.zapzup.manager.api.reset.request.GenerateTokenRequest
import br.com.zapzup.manager.service.email.IEmailService
import br.com.zapzup.manager.service.reset.IResetPasswordService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ResetPasswordController(
    private val resetPasswordService: IResetPasswordService
) : ResetPasswordApi {

    override fun generateResetToken(@RequestBody @Validated generateTokenRequest: GenerateTokenRequest) {
        resetPasswordService.generateResetToken(generateTokenRequest.toDomain())
    }
}