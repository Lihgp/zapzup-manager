package br.com.zapzup.manager.api

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseWrapper<T> (
    val content: T
)