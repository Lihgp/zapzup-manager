package br.com.zapzup.manager.application.interceptors

import br.com.zapzup.manager.commons.ResourceBundle
import br.com.zapzup.manager.commons.error.ErrorResponse
import br.com.zapzup.manager.commons.error.ZapZupErrorCode
import br.com.zapzup.manager.commons.exceptions.DuplicatedIdException
import br.com.zapzup.manager.commons.exceptions.EqualPasswordException
import br.com.zapzup.manager.commons.exceptions.InvalidOldPasswordException
import br.com.zapzup.manager.commons.exceptions.InvalidTokenException
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.commons.exceptions.ValidationException
import br.com.zapzup.manager.commons.objectToJson
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
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
    private val resourceBundle: ResourceBundle
) {

    private val log = LogManager.getLogger(this.javaClass)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun genericException(ex: Exception): ErrorResponse {
        val code = ZapZupErrorCode.GENERAL_ERROR.code
        val originalError = ex.javaClass.name + " - " + ex.message
        val message = resourceBundle.getMessage(ZapZupErrorCode.GENERAL_ERROR.key)

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

        val errorResponse = ErrorResponse(
            fields = fields,
            message = resourceBundle.getMessage(ZapZupErrorCode.METHOD_ARGUMENT_INVALID.key)
                ?: ZapZupErrorCode.METHOD_ARGUMENT_INVALID.code,
            code = ZapZupErrorCode.METHOD_ARGUMENT_INVALID.code
        )

        log.error("ErrorFormatted: ${errorResponse.objectToJson()}")

        return errorResponse
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ErrorResponse {
        log.error("UserAlreadyExistsException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.USER_ALREADY_EXISTS.key, arrayOf(ex.field))
                ?: ZapZupErrorCode.USER_ALREADY_EXISTS.code,
            code = ZapZupErrorCode.USER_ALREADY_EXISTS.code
        )
    }

    @ExceptionHandler(InvalidTokenException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleInvalidTokenException(ex: InvalidTokenException): ErrorResponse {
        log.error("InvalidTokenException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.INVALID_TOKEN.key)
                ?: ZapZupErrorCode.INVALID_TOKEN.code,
            code = ZapZupErrorCode.INVALID_TOKEN.code
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleUserNotFoundException(ex: UserNotFoundException): ErrorResponse {
        log.error("UserNotFoundException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.USER_NOT_FOUND.key)
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
            message = resourceBundle.getMessage(ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.key)
                ?: ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.code,
            code = ZapZupErrorCode.INVALID_PAGINATION_LIMIT_OFFSET.code
        )
    }

    @ExceptionHandler(InvalidOldPasswordException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleInvalidOldPasswordException(ex: InvalidOldPasswordException): ErrorResponse {
        log.error("InvalidOldPasswordException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.INVALID_OLD_PASSWORD.key)
                ?: ZapZupErrorCode.INVALID_OLD_PASSWORD.code,
            code = ZapZupErrorCode.INVALID_OLD_PASSWORD.code
        )
    }

    @ExceptionHandler(EqualPasswordException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleEqualPasswordException(ex: EqualPasswordException): ErrorResponse {
        log.error("EqualPasswordException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.EQUAL_PASSWORD.key)
                ?: ZapZupErrorCode.EQUAL_PASSWORD.code,
            code = ZapZupErrorCode.EQUAL_PASSWORD.code
        )
    }

    @ExceptionHandler(DuplicatedIdException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleEqualPasswordException(ex: DuplicatedIdException): ErrorResponse {
        log.error("DuplicatedIdException ", ex)

        return ErrorResponse(
            message = resourceBundle.getMessage(ZapZupErrorCode.DUPLICATED_ID.key, arrayOf(ex.id))
                ?: ZapZupErrorCode.DUPLICATED_ID.code,
            code = ZapZupErrorCode.DUPLICATED_ID.code
        )
    }
}