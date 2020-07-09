package br.com.zapzup.manager.application.controller.chat

import br.com.zapzup.manager.api.chat.request.CreateChatRequest
import br.com.zapzup.manager.api.chat.response.ChatResponse
import br.com.zapzup.manager.application.controller.user.toResponseList
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreateChatTO

fun CreateChatRequest.toDomain() = CreateChatTO(
    userId = this.userId,
    chatName = this.chatName
)

fun ChatTO.toResponse() = ChatResponse(
    id = this.id,
    name = this.name,
    description = this.description,
    status = this.status,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    deletedBy = this.deletedBy,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
    users = this.users.toResponseList()
)