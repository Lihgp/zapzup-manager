package br.com.zapzup.manager.service.email.impl

import br.com.zapzup.manager.commons.ResourceBundle
import br.com.zapzup.manager.domain.enums.TokenTypeEnum
import br.com.zapzup.manager.service.email.IEmailService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val resourceBundle: ResourceBundle
) : IEmailService {

    override fun sendEmail(to: String, code: String, type: TokenTypeEnum) {
        val message = SimpleMailMessage()

        message.from = "ZapZup"
        message.setTo(to)
        message.subject = resourceBundle.getMessage("subject.code.${type.name.toLowerCase()}")
        message.text = resourceBundle.getMessage("text.code", arrayOf(code))

        mailSender.send(message)
    }
}