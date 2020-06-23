package br.com.zapzup.manager.infrastructure.error

private class FieldsError(code: String, key: String) : ErrorCode(code, key)

object FieldsErrorCode{

    val MUST_NOT_BE_BLANK: ErrorCode = FieldsError("MUST_NOT_BE_BLANK", "must.not.be.blank")
    val MUST_NOT_BE_NULL: ErrorCode = FieldsError("MUST_NOT_BE_NULL", "must.not.be.null")
    val MUST_NOT_BE_EMPTY: ErrorCode = FieldsError("MUST_NOT_BE_EMPTY", "must.not.be.empty")
    val INCORRECT_SIZE: ErrorCode = FieldsError("INCORRECT_SIZE", "incorrect.size")
    val METHOD_ARGUMENT_INVALID: ErrorCode = FieldsError("METHOD_ARGUMENT_INVALID", "method.argument.invalid")
    val INVALID_EMAIL_FORMAT: ErrorCode = FieldsError("INVALID_EMAIL_FORMAT", "invalid.email.format")
    val DUPLICATED_FIELD: ErrorCode = FieldsError("DUPLICATED_FIELD", "duplicated.field")
    val INVALID_VALUE: ErrorCode = FieldsError("INVALID_VALUE", "invalid.value")
}