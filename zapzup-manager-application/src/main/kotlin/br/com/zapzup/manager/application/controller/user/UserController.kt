package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.request.UpdateUserRequest
import br.com.zapzup.manager.api.user.response.CreateUserResponse
import br.com.zapzup.manager.api.user.response.UpdateUserResponse
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.service.user.IUserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) : UserApi {

    override fun create(
        @RequestBody @Validated createUserRequest: CreateUserRequest
    ): ResponseWrapper<CreateUserResponse> =
        ResponseWrapper(userService.create(createUserTO = createUserRequest.toDomain()).toCreateUserResponse())

    override fun getUsers(email: String, username: String, name: String, page: Int, limit: Int): ResponseWrapper<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override fun update(
        @RequestBody @Validated updateUserRequest: UpdateUserRequest,
        @PathVariable(name = "id") id: String
    ): ResponseWrapper<UpdateUserResponse> =
        ResponseWrapper(userService.update(updateUserTO = updateUserRequest.toDomain(id = id)).toUpdateUserResponse())
}
