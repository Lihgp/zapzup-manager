package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.domain.to.user.CreateUserTO

interface IUserService {

    fun getUsers(filter: GetUsersFilter): List<UserTO>

    fun create(createUserTO: CreateUserTO): UserTO
}