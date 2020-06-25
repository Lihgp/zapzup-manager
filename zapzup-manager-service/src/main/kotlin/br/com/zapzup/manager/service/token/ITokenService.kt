package br.com.zapzup.manager.service.token

import br.com.zapzup.manager.domain.to.token.GenerateTokenTO
import br.com.zapzup.manager.domain.to.token.TokenTO

interface ITokenService {
    fun generateToken(generateTokenTO: GenerateTokenTO, tokenType: String)

    fun validateToken(code: String)

    fun getAll(): List<TokenTO>

    fun delete(id: String)
}