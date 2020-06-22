package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.UserRequest
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.service.user.IUserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) : UserApi {

    override fun create(@RequestBody @Validated userRequest: UserRequest): ResponseWrapper<UserResponse> =
        ResponseWrapper(userService.create(userRequest.toDomain()).toResponse())
}