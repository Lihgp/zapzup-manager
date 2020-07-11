package br.com.zapzup.manager.api.chat.request

import javax.validation.constraints.NotEmpty

data class CreateGroupChatRequest (
    @field:[NotEmpty]
    val chatName: String,
    val chatDescription: String,
    @field:[NotEmpty]
    val creatorUserId: String,
    val members: List<UserId>
) {
    data class UserId(
        val id: String
    )
}