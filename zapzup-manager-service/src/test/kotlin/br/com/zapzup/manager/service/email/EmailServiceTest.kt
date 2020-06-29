package br.com.zapzup.manager.service.email

import br.com.zapzup.manager.commons.ResourceBundle
import br.com.zapzup.manager.domain.enums.TokenTypeEnum
import br.com.zapzup.manager.service.email.impl.EmailService
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

class EmailServiceTest {
    private val mailSender: JavaMailSender = mock(JavaMailSender::class.java)
    private val messageSource: MessageSource = mock(MessageSource::class.java)
    private val resourceBundle: ResourceBundle = ResourceBundle(messageSource)
    private val emailService: IEmailService = EmailService(mailSender, resourceBundle)

    @Test
    fun `should send email with success`() {
        val to = "fulano@gmail.com"
        val code = "CODE"
        val type = TokenTypeEnum.PASSWORD
        val message = buildMessage(to)

        `when`(resourceBundle.getMessage("subject.code.${type.name.toLowerCase()}"))
            .thenReturn("Password reset")
        `when`(resourceBundle.getMessage("text.code", arrayOf(code))).thenReturn("Your token is: ")

        emailService.sendEmail(to, code, type)

        verify(mailSender, times(1)).send(message)
    }

    private fun buildMessage(to: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.from = "ZapZup"
        message.subject = "Password reset"
        message.text = "Your token is: "

        return message
    }
}