package br.com.zapzup.manager.service.reset

import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO
import br.com.zapzup.manager.domain.to.reset.ResetPasswordTokenTO

interface IResetPasswordService {
    fun generateResetToken(generateTokenTO: GenerateTokenTO)

    fun validateToken(token: String)

    fun getAll(): List<ResetPasswordTokenTO>

    fun delete(id: String)
}