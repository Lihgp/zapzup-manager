package br.com.zapzup.manager.service.scheduler

import br.com.zapzup.manager.service.token.ITokenService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

//@Component
@EnableScheduling
class ValidateTokenScheduler(
    private val tokenService: ITokenService
) {

    @Scheduled(fixedRateString = "\${reset.password.token.validation.millis}")
    fun validateTokenExpirationDate() {
        tokenService.getAll().forEach {
            if (it.expirationDate.isBefore(OffsetDateTime.now())) {
                tokenService.delete(it.id)
            }
        }
    }

}