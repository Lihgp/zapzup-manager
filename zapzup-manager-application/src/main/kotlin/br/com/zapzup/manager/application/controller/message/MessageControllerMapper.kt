package br.com.zapzup.manager.application.controller.message

import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import br.com.zapzup.manager.api.message.response.MessageResponse
import br.com.zapzup.manager.application.controller.chat.toResponse
import br.com.zapzup.manager.domain.to.message.CreateMessageTO
import br.com.zapzup.manager.domain.to.message.MessageTO
import org.springframework.web.multipart.MultipartFile

fun CreateMessageRequest.toDomain(file: MultipartFile? = null) = CreateMessageTO(
    userId = this.userId,
    chatId = this.chatId,
    content = this.content,
    file = file
)

fun MessageTO.toResponse() = MessageResponse(
    id = this.id,
    sender = this.sender,
    content = this.content,
    createdAt = this.createdAt,
    file = this.file.toResponse()
)