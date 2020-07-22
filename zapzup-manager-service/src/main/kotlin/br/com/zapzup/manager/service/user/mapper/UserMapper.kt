package br.com.zapzup.manager.service.user.mapper

import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.UserStatusEnum
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO

fun User.toTO() = userTO(user = this)

fun CreateUserTO.toEntity() = user(createUserTO = this)

fun UserTO.toEntity() = user(userTO = this)

fun user(createUserTO: CreateUserTO): User =
    User(
        name = createUserTO.name,
        username = createUserTO.username,
        email = createUserTO.email,
        password = createUserTO.password
    )

fun user(userTO: UserTO): User =
    User(
        id = userTO.id,
        name = userTO.name,
        username = userTO.username,
        note = userTO.note,
        userStatus = UserStatusEnum.valueOf(userTO.status),
        email = userTO.email,
        createdAt = userTO.createdAt,
        updatedAt = userTO.updatedAt,
        deletedAt = userTO.deletedAt
    )

fun userTO(user: User): UserTO =
    UserTO(
        id = user.id,
        name = user.name,
        username = user.username,
        note = user.note,
        status = user.userStatus.name,
        email = user.email,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        deletedAt = user.deletedAt
    )

fun List<UserTO>.listToEntity(): List<User> =
    this.map { it.toEntity() }

fun List<User>.listToTO(): List<UserTO> =
    this.map { it.toTO() }