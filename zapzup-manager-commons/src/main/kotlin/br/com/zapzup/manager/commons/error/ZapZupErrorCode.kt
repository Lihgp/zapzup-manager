package br.com.zapzup.manager.commons.error

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
    val USER_ALREADY_EXISTS: ErrorCode = ErrorCode("USER_ALREADY_EXISTS", "user.already.exists")
    val USER_NOT_FOUND: ErrorCode = ErrorCode("USER_NOT_FOUND", "user.not.found")
    val INVALID_TOKEN: ErrorCode = ErrorCode("INVALID_TOKEN", "invalid.token")
    val INVALID_OLD_PASSWORD: ErrorCode = ErrorCode("INVALID_OLD_PASSWORD", "invalid.old.password")
    val EQUAL_PASSWORD: ErrorCode = ErrorCode("EQUAL_PASSWORD", "equal.password")
    val INVALID_PAGINATION_LIMIT_OFFSET: ErrorCode =
        ErrorCode("INVALID_PAGINATION_LIMIT_OFFSET", "invalid.pagination.limit.offset")
    val DUPLICATED_ID: ErrorCode = ErrorCode("DUPLICATED_ID", "duplicated.id")
    val USER_NOT_FOUND_IN_CHAT: ErrorCode = ErrorCode("USER_NOT_FOUND_IN_CHAT", "user.not.found.in.chat")
    val SUBJECT_CODE_PASSWORD: ErrorCode = ErrorCode("SUBJECT_CODE_PASSWORD", "subject.code.password")
    val SUBJECT_CODE_EMAIL: ErrorCode = ErrorCode("SUBJECT_CODE_EMAIL", "subject.code.email")
}