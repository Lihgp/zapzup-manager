package br.com.zapzup.manager.api.chat.request

import javax.validation.constraints.NotEmpty

data class CreatePrivateChatRequest(
    @field:[NotEmpty]
    val creatorUserId: String,
    @field:[NotEmpty]
    val memberId: String
)