package br.com.zapzup.manager.application.exceptions

import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.error.ErrorResponse
import br.com.zapzup.manager.commons.error.ZapZupErrorCode
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.commons.exceptions.ValidationException
import org.apache.logging.log4j.LogManager
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.LinkedList

@ControllerAdvice
class ZapZupExceptionHandler(
    private val messageSource: MessageSource
) {

    private val log = LogManager.getLogger(this.javaClass)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun genericException(ex: Exception): ErrorResponse {
        val code = ZapZupErrorCode.GENERAL_ERROR.code
        val originalError = ex.javaClass.name + " - " + ex.message
        val message = getMessage(ZapZupErrorCode.GENERAL_ERROR.key)

        log.error("Exception: {}", originalError, ex)

        return ErrorResponse(
            code = code, message = message ?: code, originalError = originalError
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ErrorResponse {
        val fields = LinkedHashMap<String, List<String>>()

        log.error("MethodArgumentNotValidException ", ex)

        ex.bindingResult.fieldErrors.forEach { field ->
            fields.computeIfAbsent(field.field) {
                LinkedList<String>().also { f ->
                    when (field.code) {
                        ("NotNull") -> f.add(ZapZupErrorCode.MUST_NOT_BE_NULL.code)
                        ("NotBlank") -> f.add(ZapZupErrorCode.MUST_NOT_BE_BLANK.code)
                        ("NotEmpty") -> f.add(ZapZupErrorCode.MUST_NOT_BE_EMPTY.code)
                        ("Size") -> f.add(ZapZupErrorCode.INCORRECT_SIZE.code)
                        ("Email") -> f.add(ZapZupErrorCode.INVALID_EMAIL_FORMAT.code)
                        else -> f.add(ZapZupErrorCode.METHOD_ARGUMENT_INVALID.code)
                    }
                }
            }
        }

        return ErrorResponse(
            fields = fields,
            message = getMessage(ZapZupErrorCode.METHOD_ARGUMENT_INVALID.key)
                ?: ZapZupErrorCode.METHOD_ARGUMENT_INVALID.code,
            code = ZapZupErrorCode.METHOD_ARGUMENT_INVALID.code
        )
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ErrorResponse {
        log.error("UserAlreadyExistsException ", ex)

        return ErrorResponse(
            message = getMessage(ZapZupErrorCode.CUSTOMER_ALREADY_EXISTS.key, arrayOf(ex.field))
                ?: ZapZupErrorCode.CUSTOMER_ALREADY_EXISTS.code,
            code = ZapZupErrorCode.CUSTOMER_ALREADY_EXISTS.code
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleUserNotFoundException(ex: UserNotFoundException): ErrorResponse {
        log.error("UserNotFoundException ", ex)

        return ErrorResponse(
            message = getMessage(ZapZupErrorCode.USER_NOT_FOUND.key, arrayOf(ex.id))
                ?: ZapZupErrorCode.USER_NOT_FOUND.code,
            code = ZapZupErrorCode.USER_NOT_FOUND.code
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun handleValidationException(ex: ValidationException): ErrorResponse {
        log.error("ValidationException ", ex)

        return ErrorResponse(
            message = getMessage(ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.key)
                ?: ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.code,
            code = ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.code
        )
    }

    private fun getMessage(key: String, args: Array<String> = arrayOf()): String?
        = messageSource.getMessage(key, args, LocaleContextHolder.getLocale())
}