package br.com.zapzup.manager.domain.to.message

data class CreateMessageTO(
    val userId: String = "",
    val chatId: String = "",
    val content: String = ""
)