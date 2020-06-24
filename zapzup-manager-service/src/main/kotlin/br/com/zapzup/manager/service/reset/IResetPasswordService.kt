package br.com.zapzup.manager.service.reset

import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO

interface IResetPasswordService {
    fun generateResetToken(generateTokenTO: GenerateTokenTO)
}