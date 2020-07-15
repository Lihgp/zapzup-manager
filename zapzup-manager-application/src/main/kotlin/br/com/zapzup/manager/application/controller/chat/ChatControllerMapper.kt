package br.com.zapzup.manager.application.controller.chat

import br.com.zapzup.manager.api.chat.request.CreatePrivateChatRequest
import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.response.ChatResponse
import br.com.zapzup.manager.api.chat.response.FileResponse
import br.com.zapzup.manager.application.controller.user.toResponseList
import br.com.zapzup.manager.commons.utils.FileUtils.decompressBytes
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreatePrivateChatTO
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import br.com.zapzup.manager.domain.to.file.FileTO

fun CreatePrivateChatRequest.toDomain() = CreatePrivateChatTO(
    creatorUserId = this.creatorUserId,
    memberId = this.memberId
)

fun CreateGroupChatRequest.toDomain() = CreateGroupChatTO(
    name = this.name,
    description = this.description,
    creatorUserId = this.creatorUserId,
    members = this.members.map { it.toUserIdTO() }
)

fun ChatTO.toResponse() = ChatResponse(
    id = this.id,
    name = this.name,
    description = this.description,
    createdBy = this.createdBy,
    updatedBy = this.updatedBy,
    deletedBy = this.deletedBy,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
    users = this.users.toResponseList()
)

fun FileTO?.toResponse(): FileResponse? {
    if (this == null)   return null

    return FileResponse(
        id = this.id,
        name = this.name,
        type = this.type,
        fileByte = decompressBytes(this.fileByte),
        createdAt = this.createdAt
    )
}

private fun CreateGroupChatRequest.UserId.toUserIdTO(): CreateGroupChatTO.UserIdTO =
    CreateGroupChatTO.UserIdTO(
        id = this.id
    )
