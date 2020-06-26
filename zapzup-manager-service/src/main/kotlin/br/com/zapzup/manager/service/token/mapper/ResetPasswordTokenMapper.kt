package br.com.zapzup.manager.service.token.mapper

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.to.token.TokenTO

fun Token.toTO() = toTokenTO(token = this)

fun toTokenTO(token: Token): TokenTO =
    TokenTO(
        id = token.id,
        code = token.code,
        expirationDate = token.expirationDate!!
    )

fun toTokenTOList(resetPasswordToken: List<Token>): List<TokenTO> =
    resetPasswordToken.map { it.toTO() }