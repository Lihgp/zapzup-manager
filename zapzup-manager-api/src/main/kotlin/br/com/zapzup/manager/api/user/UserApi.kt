package br.com.zapzup.manager.api.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.request.UpdateUserRequest
import br.com.zapzup.manager.api.user.request.UpdatePasswordRequest
import br.com.zapzup.manager.api.user.response.CreateUserResponse
import br.com.zapzup.manager.api.user.response.UpdateUserResponse
import br.com.zapzup.manager.api.user.response.InvalidPasswordResponse
import br.com.zapzup.manager.api.user.response.UserAlreadyExistsResponse
import br.com.zapzup.manager.api.user.response.UserNotFoundResponse
import br.com.zapzup.manager.api.user.response.UserResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "User", tags = ["User"], description = "User Resources")
@RequestMapping(value = ["/users"])
interface UserApi {

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a user")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created"),
        ApiResponse(code = 422, message = "User Already Exists", response = UserAlreadyExistsResponse::class)
    ])
    fun create(@RequestBody @Validated createUserRequest: CreateUserRequest): ResponseWrapper<CreateUserResponse>

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @ApiOperation(value = "Gets a users")
    @ApiResponses()
    fun getUsers(
        @RequestParam(name = "email", required = false) email: String,
        @RequestParam(name = "username", required = false) username: String,
        @RequestParam(name = "name", required = false) name: String,
        @RequestParam(name = "page", defaultValue = "1", required = false) page: Int,
        @RequestParam(name = "limit", defaultValue = "10", required = false) limit: Int
    ): ResponseWrapper<List<UserResponse>>

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    @ApiOperation(value = "Updates a user")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Updated"),
        ApiResponse(code = 404, message = "User not found", response = UserNotFoundResponse::class)
    ])
    fun update(@RequestBody @Validated updateUserRequest: UpdateUserRequest,
               @PathVariable(name = "id") id: String): ResponseWrapper<UpdateUserResponse>

    @PutMapping(value = ["/{id}/update-password"])
    @ResponseBody
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "Updates the user's password")
    @ApiResponses(value = [
        ApiResponse(code = 204, message = "Updated"),
        ApiResponse(code = 422, message = "Invalid Password", response = InvalidPasswordResponse::class),
        ApiResponse(code = 404, message = "User not found", response = UserNotFoundResponse::class)
    ])
    fun updatePassword(@RequestBody updatePasswordRequest: UpdatePasswordRequest, @PathVariable id: String)
}
