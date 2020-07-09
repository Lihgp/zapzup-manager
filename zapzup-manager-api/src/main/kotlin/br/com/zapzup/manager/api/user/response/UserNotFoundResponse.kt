package br.com.zapzup.manager.api.user.response

import io.swagger.annotations.ApiModelProperty

data class UserNotFoundResponse(
    @ApiModelProperty(example = "USER_NOT_FOUND")
    val code: String,
    @ApiModelProperty(example = "User not found")
    val message: String
)