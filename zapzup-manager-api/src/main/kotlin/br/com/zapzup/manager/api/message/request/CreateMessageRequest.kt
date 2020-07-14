package br.com.zapzup.manager.api.message.request

import javax.validation.constraints.NotEmpty

data class CreateMessageRequest(
    @field:[NotEmpty]
    val userId: String,
    @field:[NotEmpty]
    val chatId: String,
    @field:[NotEmpty]
    val content: String
)