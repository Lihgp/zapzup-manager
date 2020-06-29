package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO

interface IUserService {

    fun create(createUserTO: CreateUserTO): UserTO

    fun update(updateUserTO: UpdateUserTO) : UserTO
}