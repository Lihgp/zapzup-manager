package br.com.zapzup.manager.service.user.mapper

import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO

fun User.toTO() = userTO(user = this)

fun CreateUserTO.toEntity() = user(createUserTO = this)

fun user(createUserTO: CreateUserTO): User =
    User(
        name = createUserTO.name,
        username = createUserTO.username,
        email = createUserTO.email,
        password = createUserTO.password
    )

fun userTO(user: User): UserTO =
    UserTO(
        id = user.id,
        name = user.name,
        username = user.username,
        note = user.note,
        status = user.status.name,
        email = user.email,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        deletedAt = user.deletedAt
    )