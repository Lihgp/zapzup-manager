package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO

interface IUserService {

    fun getUsers(filter: GetUsersFilter): List<UserTO>
}