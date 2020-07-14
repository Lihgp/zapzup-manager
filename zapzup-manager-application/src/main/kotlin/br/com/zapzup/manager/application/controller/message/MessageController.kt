package br.com.zapzup.manager.application.controller.message

import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import br.com.zapzup.manager.service.message.IMessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RestController


@RestController
class MessageController(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val messageService: IMessageService
) {

    @MessageMapping(value = ["messages.send"])
    fun sendMessage(@Payload createMessageRequest: CreateMessageRequest) {
        val messageResponse = messageService.save(createMessageRequest.toDomain()).toResponse()

        messagingTemplate.convertAndSend("/topic/private/${createMessageRequest.chatId}", messageResponse)
    }
}