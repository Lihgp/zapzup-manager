package br.com.zapzup.manager.domain.to.chat

data class CreatePrivateChatTO(
    val creatorUserId: String,
    val memberId: String
)