package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.request.UpdateUserRequest
import br.com.zapzup.manager.api.user.request.UpdatePasswordRequest
import br.com.zapzup.manager.api.user.response.CreateUserResponse
import br.com.zapzup.manager.api.user.response.UpdateUserResponse
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.service.user.IUserService
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) : UserApi {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun create(
        @RequestBody @Validated createUserRequest: CreateUserRequest
    ): ResponseWrapper<CreateUserResponse> {
        log.info("CreateUserRequest: ${createUserRequest.copy(password = "")}")

        val userCreated = userService.create(createUserTO = createUserRequest.toDomain())

        log.info("UserCreated: $userCreated")

        return ResponseWrapper(userCreated.toCreateUserResponse())
    }

    override fun getUsers(email: String, username: String, name: String, page: Int, limit: Int): ResponseWrapper<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override fun update(
        @RequestBody @Validated updateUserRequest: UpdateUserRequest,
        @PathVariable(name = "id") id: String
    ): ResponseWrapper<UpdateUserResponse> {
        log.info("UpdateUserRequest: $updateUserRequest")

        val userUpdated = userService.update(updateUserTO = updateUserRequest.toDomain(id = id))

        log.info("UserUpdated: $userUpdated")

        return ResponseWrapper(userUpdated.toUpdateUserResponse())
    }

    override fun updatePassword(
        @RequestBody updatePasswordRequest: UpdatePasswordRequest, @PathVariable(name = "id") id: String) {
        userService.updatePassword(id, updatePasswordRequest.toDomain())
    }

    override fun delete(@PathVariable(value = "id") id: String) {
        log.info("userId for delete: $id")

        userService.delete(id)
    }
}
