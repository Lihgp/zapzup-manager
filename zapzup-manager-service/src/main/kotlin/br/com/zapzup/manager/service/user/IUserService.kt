package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import org.springframework.data.domain.Page

interface IUserService {

    fun getUsers(filter: GetUsersFilter): Page<UserTO>

    fun getUserById(userId: String): UserTO?

    fun create(createUserTO: CreateUserTO): UserTO
}