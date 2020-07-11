package br.com.zapzup.manager.api.chat

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.request.CreatePrivateChatRequest
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartFile

@Api(value = "Chat", tags = ["Chat"], description = "Chat Resources")
@RequestMapping(value = ["/chats"])
interface ChatApi {

    @PostMapping(value = ["/private"])
    @ResponseBody
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a private chat")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created")
    ])
    fun createPrivateChat(
        @RequestBody @Validated createPrivateChatRequest: CreatePrivateChatRequest
    ): ResponseWrapper<ChatResponse>

    @PostMapping(value = ["/group"])
    @ResponseBody
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a group chat")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created")
    ])
    fun createGroupChat(
        @RequestParam createGroupChatRequest: String,
        @RequestParam(value = "groupIcon") icon: MultipartFile
    ): ResponseWrapper<ChatResponse>
}