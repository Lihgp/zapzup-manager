package br.com.zapzup.manager.infrastructure.error

import org.springframework.util.Assert

open class ErrorCode(
    val code: String,
    val key: String,
    val parameters: Array<String> = arrayOf()
) {
    init {
        Assert.notNull(key, "code can't be null")
        Assert.hasText(key, "key can't be empty")
        Assert.notNull(parameters, "parameters can't be null")
    }

    fun withParameters(parameters: Array<String>): ErrorCode {
        return ErrorCode(code, key, parameters)
    }

    override fun toString(): String {
        return this.key
    }

    companion object {

        val DUPLICATED = ErrorCode("DUPLICATED", "duplicated.field")
        val PAYLOAD_INVALID = ErrorCode("PAYLOAD_INVALID", "payload.invalid")
        val MISSING_PARAMETER = ErrorCode("MISSING_PARAMETER", "missing.parameter")
    }
}