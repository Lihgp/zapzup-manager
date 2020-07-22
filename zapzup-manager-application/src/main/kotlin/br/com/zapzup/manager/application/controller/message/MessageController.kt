package br.com.zapzup.manager.application.controller.message

import br.com.zapzup.manager.api.message.MessageApi
import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import br.com.zapzup.manager.commons.jsonToObject
import br.com.zapzup.manager.service.chat.IChatService
import br.com.zapzup.manager.service.message.IMessageService
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class MessageController(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val messageService: IMessageService,
    private val chatService: IChatService
) : MessageApi {

    override fun sendMessage(@Payload createMessageRequest: CreateMessageRequest) {
        val messageResponse = messageService.save(createMessageRequest.toDomain()).toResponse()
        val chatId = createMessageRequest.chatId

        messagingTemplate.convertAndSend("/topic/private/${chatId}", messageResponse)

        chatService.sendToUsersChatsOrderedByLastMessageSent(chatId)
    }

    override fun sendFileMessage(
        @RequestParam(required = false) createMessageRequest: String,
        @RequestParam(required = false) file: MultipartFile?
    ) {
        val createMessageRequestObject = createMessageRequest.jsonToObject(CreateMessageRequest::class.java)
        val messageResponse = messageService.save(createMessageRequestObject!!.toDomain(file)).toResponse()
        val chatId = createMessageRequestObject.chatId

        messagingTemplate.convertAndSend("/topic/private/${chatId}", messageResponse)

        chatService.sendToUsersChatsOrderedByLastMessageSent(chatId)
    }
}