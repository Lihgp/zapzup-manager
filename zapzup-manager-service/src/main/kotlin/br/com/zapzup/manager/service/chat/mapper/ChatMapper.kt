package br.com.zapzup.manager.service.chat.mapper

import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.service.user.mapper.listToEntity
import br.com.zapzup.manager.service.user.mapper.listToTO

fun Chat.toTO() = chatTO(chat = this)

fun ChatTO.toEntity() = chat(chatTO = this)

fun List<Chat>.toTOList() = chatsTO(chats = this)

fun chat(chatTO: ChatTO): Chat =
    Chat(
        id = chatTO.id,
        name = chatTO.name,
        description = chatTO.description,
        createdBy = chatTO.createdBy,
        updatedBy = chatTO.updatedBy,
        deletedBy = chatTO.deletedBy,
        createdAt = chatTO.createdAt,
        updatedAt = chatTO.updatedAt,
        deletedAt = chatTO.deletedAt,
        lastMessageSentAt = chatTO.lastMessageSentAt,
        users = chatTO.users.listToEntity().toMutableList()
    )

fun chatsTO(chats: List<Chat>): List<ChatTO> = chats.map { it.toTO() }

fun chatTO(chat: Chat): ChatTO =
    ChatTO(
        id = chat.id,
        name = chat.name,
        description = chat.description,
        createdBy = chat.createdBy,
        updatedBy = chat.updatedBy,
        deletedBy = chat.deletedBy,
        createdAt = chat.createdAt,
        updatedAt = chat.updatedAt,
        deletedAt = chat.deletedAt,
        lastMessageSentAt = chat.lastMessageSentAt,
        users = chat.users.listToTO()
    )