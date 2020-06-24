package br.com.zapzup.manager.service.email

interface IEmailService {
    fun sendEmail(to: String, token: String)
}