package br.com.zapzup.manager.commons

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class ResourceBundle(
    private val messageSource: MessageSource
) {

    fun getMessage(message: String, args: Array<String> = arrayOf()): String? = messageSource.getMessage(message, args, LocaleContextHolder.getLocale())
}