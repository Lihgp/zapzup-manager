package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.response.CreateUserResponse
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.service.user.IUserService
import org.springframework.data.domain.Page
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) : UserApi {

    override fun create(
        @RequestBody @Validated createUserRequest: CreateUserRequest
    ): ResponseWrapper<CreateUserResponse> =
        ResponseWrapper(userService.create(createUserTO = createUserRequest.toDomain()).toCreateUserResponse())

    override fun getUsers(
        @RequestParam(name = "email", defaultValue = "", required = false) email: String,
        @RequestParam(name = "username", defaultValue = "", required = false) username: String,
        @RequestParam(name = "name", defaultValue = "", required = false) name: String,
        @RequestParam(name = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(name = "limit", defaultValue = "10", required = false) limit: Int
    ): ResponseWrapper<Page<UserResponse>> {

        val filter = GetUsersFilter(
            email = email,
            username = username,
            name = name,
            page = page,
            limit = limit
        )

        return ResponseWrapper(userService.getUsers(filter).map { user -> user.toResponse() })
    }

    override fun getUserById(
        @RequestParam(name = "userId", required = true) userId: String
    ): ResponseWrapper<UserResponse?> {
        return ResponseWrapper(userService.getUserById(userId)?.toResponse())
    }
}