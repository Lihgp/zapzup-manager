package br.com.zapzup.manager.api.user.response

import io.swagger.annotations.ApiModelProperty

data class InvalidPasswordResponse (
    @ApiModelProperty(example = "INVALID_NEW_PASSWORD")
    val code: String,
    @ApiModelProperty(example = "New password equals to old password")
    val message: String
)