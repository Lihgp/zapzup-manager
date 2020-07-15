package br.com.zapzup.manager.application.controller.message

import br.com.zapzup.manager.api.message.MessageApi
import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import br.com.zapzup.manager.commons.jsonToObject
import br.com.zapzup.manager.service.message.IMessageService
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class MessageController(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val messageService: IMessageService
) : MessageApi {

    override fun sendMessage(@Payload createMessageRequest: CreateMessageRequest) {
        val messageResponse = messageService.save(createMessageRequest.toDomain()).toResponse()

        messagingTemplate.convertAndSend("/topic/private/${createMessageRequest.chatId}", messageResponse)
    }

    override fun sendFileMessage(
        @RequestParam(required = false) createMessageRequest: String,
        @RequestParam file: MultipartFile
    ) {
        val createMessageRequestObject = createMessageRequest.jsonToObject(CreateMessageRequest::class.java)
        val messageResponse = messageService.save(createMessageRequestObject!!.toDomain(file)).toResponse()

        messagingTemplate.convertAndSend("/topic/private/${createMessageRequestObject.chatId}", messageResponse)
    }
}