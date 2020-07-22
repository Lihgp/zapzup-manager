package br.com.zapzup.manager.api.message

import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Api(value = "Message", tags = ["Message"], description = "Message Resources")
@RequestMapping(value = ["/messages"])
interface MessageApi {

    //    @PostMapping
    @MessageMapping(value = ["messages.send"])
    @ApiOperation(value = "Sends a message into chat")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Sended")
    ])
    fun sendMessage(@Payload createMessageRequest: CreateMessageRequest)

    @PostMapping
    @ApiOperation(value = "Sends a message with or without file in chat")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Sended")
    ])
    fun sendFileMessage(
        @RequestParam createMessageRequest: String,
        @RequestParam file: MultipartFile?
    )
}