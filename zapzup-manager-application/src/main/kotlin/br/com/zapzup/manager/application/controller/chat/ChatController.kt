package br.com.zapzup.manager.application.controller.chat

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.chat.ChatApi
import br.com.zapzup.manager.api.chat.request.CreateChatRequest
import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.response.ChatResponse
import br.com.zapzup.manager.service.chat.IChatService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: IChatService
) : ChatApi {
    override fun create(
        @RequestBody @Validated createGroupChatRequest: CreateGroupChatRequest
    ): ResponseWrapper<ChatResponse> =
        ResponseWrapper(
            chatService.createGroupChat(
                createGroupChatTO = createGroupChatRequest.toDomain()
            ).toResponse()
        )
}