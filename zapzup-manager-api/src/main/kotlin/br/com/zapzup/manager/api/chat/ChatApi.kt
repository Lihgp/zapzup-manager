package br.com.zapzup.manager.api.chat

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.chat.request.CreateChatRequest
import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.response.ChatResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "Chat", tags = ["Chat"], description = "Chat Resources")
@RequestMapping(value = ["/chats"])
interface ChatApi {

    @PostMapping(value = ["/group"])
    @ResponseBody
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a group chat")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created")
    ])
    fun create(@RequestBody @Validated createGroupChatRequest: CreateGroupChatRequest): ResponseWrapper<ChatResponse>
}