package br.com.zapzup.manager.infrastructure.error

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.annotations.ApiModel

@ApiModel(value = "Error Response")
data class ErrorResponse (
    val code: String,
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val fields: Map<String, List<String>>? = null,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val originalError: String? = null
)