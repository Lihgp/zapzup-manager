package br.com.zapzup.manager.service.email.impl

import br.com.zapzup.manager.commons.ResourceBundle
import br.com.zapzup.manager.service.email.IEmailService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val resourceBundle: ResourceBundle
) : IEmailService {

    override fun sendEmail(to: String, token: String) {
        val message = SimpleMailMessage()
        message.from = "ZapZup"
        message.setTo(to)
        message.subject = resourceBundle.getMessage("subject.reset.password")
        message.text = resourceBundle.getMessage("text.reset.password", arrayOf(token))

        mailSender.send(message)
    }
}