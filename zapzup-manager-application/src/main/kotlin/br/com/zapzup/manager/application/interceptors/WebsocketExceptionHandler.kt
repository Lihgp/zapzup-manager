package br.com.zapzup.manager.application.interceptors

import br.com.zapzup.manager.commons.ResourceBundle
import br.com.zapzup.manager.commons.error.ErrorResponse
import br.com.zapzup.manager.commons.error.ZapZupErrorCode
import br.com.zapzup.manager.commons.exceptions.UserNotFoundInChatException
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class WebsocketExceptionHandler(
    private val resourceBundle: ResourceBundle,
    private val messagingTemplate: SimpMessageSendingOperations
) {

    @MessageExceptionHandler(Exception::class)
    fun genericException(ex: Exception) {
        val code = ZapZupErrorCode.GENERAL_ERROR.code
        val originalError = ex.javaClass.name + " - " + ex.message
        val message = resourceBundle.getMessage(ZapZupErrorCode.GENERAL_ERROR.key)

        val errorResponse = ErrorResponse(
            code = code, message = message ?: code, originalError = originalError
        )

        messagingTemplate.convertAndSend("/topic/error", errorResponse)
    }

    @MessageExceptionHandler(UserNotFoundInChatException::class)
    fun handleUserNotFoundInChatException(ex: UserNotFoundInChatException) {
        val errorResponse = ErrorResponse(
            message = resourceBundle.getMessage(
                ZapZupErrorCode.USER_NOT_FOUND_IN_CHAT.key, arrayOf(ex.username, ex.chatId)
            ) ?: ZapZupErrorCode.USER_NOT_FOUND_IN_CHAT.code,
            code = ZapZupErrorCode.USER_NOT_FOUND_IN_CHAT.code
        )

        messagingTemplate.convertAndSend("/topic/error", errorResponse)
    }
}