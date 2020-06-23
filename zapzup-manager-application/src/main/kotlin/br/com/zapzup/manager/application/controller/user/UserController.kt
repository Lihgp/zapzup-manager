package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.service.user.impl.UserService
import org.springframework.web.bind.annotation.RequestParam

class UserController (
    private val userService: UserService
) : UserApi {

    override fun getUsers(
        @RequestParam(name = "email", required = false) email: String,
        @RequestParam(name = "username", required = false) username: String,
        @RequestParam(name = "name", required = false) name: String,
        @RequestParam(name = "page", defaultValue = "1", required = false) page: Int,
        @RequestParam(name = "limit", defaultValue = "10", required = false) limit: Int
    ): ResponseWrapper<List<UserResponse>> {

        val filter = GetUsersFilter(
            email = email,
            username = username,
            name = name,
            page = page,
            limit = limit
        )

        return ResponseWrapper(userService.getUsers(filter))
    }
}