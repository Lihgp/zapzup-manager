package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.user.request.UserRequest
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO

fun UserRequest.toDomain() = CreateUserTO(
    name = this.name,
    username = this.username,
    email = this.email,
    password = this.password
)

fun UserTO.toResponse() =
    UserResponse(
        id = this.id,
        name = this.name,
        username = this.username,
        email = this.email,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )