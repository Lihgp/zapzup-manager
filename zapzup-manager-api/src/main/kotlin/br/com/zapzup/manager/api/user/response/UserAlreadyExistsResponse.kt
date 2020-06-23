package br.com.zapzup.manager.api.user.response

import io.swagger.annotations.ApiModelProperty

data class UserAlreadyExistsResponse(
    @ApiModelProperty(example = "USER_ALREADY_EXISTS")
    val code: String,
    @ApiModelProperty(example = "User already exists for the following field: [username/email]")
    val message: String
)