package br.com.zapzup.manager.commons.exceptions

class UserNotFoundInChatException(
    val username: String,
    val chatId: String
) : RuntimeException()