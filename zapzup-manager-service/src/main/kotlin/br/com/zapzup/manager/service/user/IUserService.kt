package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.domain.to.user.UpdatePasswordTO
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import org.springframework.data.domain.Page

interface IUserService {

    fun getUsers(filter: GetUsersFilter): Page<UserTO>

    fun getUserById(userId: String): UserTO

    fun create(createUserTO: CreateUserTO): UserTO

    fun getByEmail(email: String): UserTO

    fun updatePassword(id: String, updatePasswordTO: UpdatePasswordTO)

    fun update(updateUserTO: UpdateUserTO) : UserTO

    fun delete(id: String)
}