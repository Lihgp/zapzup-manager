package br.com.zapzup.manager.service.chat.mapper

import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.service.user.mapper.listToTO

fun Chat.toTO() = chatTO(chat = this)

fun chatTO(chat: Chat): ChatTO =
    ChatTO(
        id = chat.id,
        name = chat.name,
        description = chat.description,
        status = chat.status.name,
        createdBy = chat.createdBy,
        updatedBy = chat.updatedBy,
        deletedBy = chat.deletedBy,
        createdAt = chat.createdAt,
        updatedAt = chat.updatedAt,
        deletedAt = chat.deletedAt,
        users = chat.users.listToTO(),
        messages = chat.messages
)