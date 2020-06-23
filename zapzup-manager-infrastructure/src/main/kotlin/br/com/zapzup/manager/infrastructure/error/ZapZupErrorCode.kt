package br.com.zapzup.manager.infrastructure.error

class GeneralError(code: String, key: String) : ErrorCode(code, key)

object ZapZupErrorCode {
    val GENERAL_ERROR: ErrorCode = GeneralError("GENERAL_ERROR", "general.error")
    val METHOD_ARGUMENT_INVALID: ErrorCode = GeneralError("METHOD_ARGUMENT_INVALID", "method.argument.invalid")
    val CUSTOMER_ALREADY_EXISTS: ErrorCode = GeneralError("CUSTOMER_ALREADY_EXISTS", "customer.already.exists")
}