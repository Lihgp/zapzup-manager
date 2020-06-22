package br.com.zapzup.manager.service.test.user

import br.com.zapzup.manager.domain.to.UserTO

interface IUserService {

    fun create(userTO: UserTO): UserTO
}