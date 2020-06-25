package br.com.zapzup.manager.service.reset.mapper

import br.com.zapzup.manager.domain.entity.ResetPasswordToken
import br.com.zapzup.manager.domain.to.reset.ResetPasswordTokenTO

fun ResetPasswordToken.toTO() = toResetPasswordTokenTO(resetPasswordToken = this)

fun toResetPasswordTokenTO(resetPasswordToken: ResetPasswordToken): ResetPasswordTokenTO =
    ResetPasswordTokenTO(
        id = resetPasswordToken.id,
        token = resetPasswordToken.token,
        expirationDate = resetPasswordToken.expirationDate!!
    )

fun toResetPasswordTokenTOList(resetPasswordToken: List<ResetPasswordToken>): List<ResetPasswordTokenTO> =
    resetPasswordToken.map { it.toTO() }