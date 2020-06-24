package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.UserRequest
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.service.user.IUserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) : UserApi {

    override fun create(@RequestBody @Validated userRequest: UserRequest): ResponseWrapper<UserResponse> =
        ResponseWrapper(userService.create(userRequest.toDomain()).toResponse())

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

        return ResponseWrapper(toResponseList(userService.getUsers(filter)))
    }
}