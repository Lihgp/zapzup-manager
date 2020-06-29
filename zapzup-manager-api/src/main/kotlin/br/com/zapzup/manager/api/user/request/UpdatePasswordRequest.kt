package br.com.zapzup.manager.api.user.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:[NotEmpty]
    @field:[Size(min = 8)]
    val oldPassword: String,
    @field:[NotEmpty]
    @field:[Size(min = 8)]
    val newPassword: String
)