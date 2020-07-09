package br.com.zapzup.manager.api.chat.request

data class CreateChatRequest(
    val userId: String,
    val chatName: String
)