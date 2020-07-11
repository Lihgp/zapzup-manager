package br.com.zapzup.manager.application.controller.chat

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.chat.ChatApi
import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.response.ChatResponse
import br.com.zapzup.manager.commons.exceptions.DeserializationException
import br.com.zapzup.manager.commons.jsonToObject
import br.com.zapzup.manager.service.chat.IChatService
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class ChatController(
    private val chatService: IChatService
) : ChatApi {
    override fun createGroupChat(
        @RequestParam createGroupChatRequest: String,
        @RequestParam(value = "group-icon") icon: MultipartFile
    ): ResponseWrapper<ChatResponse> {
        val createGroupChatRequestObject = createGroupChatRequest.jsonToObject(CreateGroupChatRequest::class.java)
            ?: throw DeserializationException(CreateGroupChatRequest::class.simpleName.toString())

        return ResponseWrapper(
            chatService.createGroupChat(
                createGroupChatTO = createGroupChatRequestObject.toDomain(),
                groupIcon = icon
            ).toResponse()
        )
    }
}
