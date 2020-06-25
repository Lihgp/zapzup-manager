package br.com.zapzup.manager.service.reset

import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO
import br.com.zapzup.manager.domain.to.reset.TokenTO

interface ITokenService {
    fun generateToken(generateTokenTO: GenerateTokenTO, tokenType: String)

    fun validateToken(code: String)

    fun getAll(): List<TokenTO>

    fun delete(id: String)
}