package br.com.zapzup.manager.infrastructure.error

class ErrorCode(val code: String, val key: String)

object ZapZupErrorCode {
    val MUST_NOT_BE_BLANK: ErrorCode = ErrorCode("MUST_NOT_BE_BLANK", "must.not.be.blank")
    val MUST_NOT_BE_NULL: ErrorCode = ErrorCode("MUST_NOT_BE_NULL", "must.not.be.null")
    val MUST_NOT_BE_EMPTY: ErrorCode = ErrorCode("MUST_NOT_BE_EMPTY", "must.not.be.empty")
    val INCORRECT_SIZE: ErrorCode = ErrorCode("INCORRECT_SIZE", "incorrect.size")
    val INVALID_EMAIL_FORMAT: ErrorCode = ErrorCode("INVALID_EMAIL_FORMAT", "invalid.email.format")
    val DUPLICATED_FIELD: ErrorCode = ErrorCode("DUPLICATED_FIELD", "duplicated.field")
    val INVALID_VALUE: ErrorCode = ErrorCode("INVALID_VALUE", "invalid.value")

    val GENERAL_ERROR: ErrorCode = ErrorCode("GENERAL_ERROR", "general.error")
    val METHOD_ARGUMENT_INVALID: ErrorCode = ErrorCode("METHOD_ARGUMENT_INVALID", "method.argument.invalid")
    val CUSTOMER_ALREADY_EXISTS: ErrorCode = ErrorCode("CUSTOMER_ALREADY_EXISTS", "customer.already.exists")
}