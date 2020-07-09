package br.com.zapzup.manager.application.controller.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.UserApi
import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.request.UpdatePasswordRequest
import br.com.zapzup.manager.api.user.request.UpdateUserRequest
import br.com.zapzup.manager.api.user.response.CreateUserResponse
import br.com.zapzup.manager.api.user.response.UpdateUserResponse
import br.com.zapzup.manager.api.user.response.UserResponse
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.service.user.IUserService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping(value = ["/{id}"])
    override fun getUserById(
        @PathVariable(name = "id", required = true) userId: String
    ): ResponseWrapper<UserResponse> {
        return ResponseWrapper(userService.getUserById(userId).toResponse())
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