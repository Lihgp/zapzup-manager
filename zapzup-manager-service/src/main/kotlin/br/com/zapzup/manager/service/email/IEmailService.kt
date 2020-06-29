package br.com.zapzup.manager.service.email

import br.com.zapzup.manager.domain.enums.TokenTypeEnum

interface IEmailService {
    fun sendEmail(to: String, code: String, type: TokenTypeEnum)
}