package br.com.zapzup.manager.service.reset.impl

import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO
import br.com.zapzup.manager.repository.ResetPasswordTokenRepository
import br.com.zapzup.manager.service.reset.IResetPasswordService
import br.com.zapzup.manager.service.user.IUserService
import org.springframework.stereotype.Service

@Service
class ResetPasswordService(
    private val resetPasswordTokenRepository: ResetPasswordTokenRepository,
    private val userService: IUserService
) : IResetPasswordService {

    override fun generateResetToken(generateTokenTO: GenerateTokenTO) {

    }
}