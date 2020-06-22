package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.UserTO

interface IUserService {

    fun create(userTO: UserTO): UserTO
}