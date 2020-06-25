package br.com.zapzup.manager.service.reset.mapper

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.to.reset.TokenTO

fun Token.toTO() = toTokenTO(token = this)

fun toTokenTO(token: Token): TokenTO =
    TokenTO(
        id = token.id,
        token = token.code,
        expirationDate = token.expirationDate!!
    )

fun toResetPasswordTokenTOList(resetPasswordToken: List<Token>): List<TokenTO> =
    resetPasswordToken.map { it.toTO() }