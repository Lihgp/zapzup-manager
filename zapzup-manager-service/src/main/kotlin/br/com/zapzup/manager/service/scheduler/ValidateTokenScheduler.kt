package br.com.zapzup.manager.service.scheduler

import br.com.zapzup.manager.service.reset.IResetPasswordService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
@EnableScheduling
class ValidateTokenScheduler(
    private val resetPasswordService: IResetPasswordService
) {

    @Scheduled(fixedRateString = "\${reset.password.token.validation.millis}")
    fun validateTokens() {
        resetPasswordService.getAll().forEach {
            if (it.expirationDate.isBefore(OffsetDateTime.now())) {
                resetPasswordService.delete(it.id)
            }
        }
    }

}