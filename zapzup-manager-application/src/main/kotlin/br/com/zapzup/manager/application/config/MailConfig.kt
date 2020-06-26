package br.com.zapzup.manager.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
open class MailConfig {

    @Bean
    open fun javaMailSender(): JavaMailSender = JavaMailSenderImpl()
}