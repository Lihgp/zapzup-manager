package br.com.zapzup.manager.api.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.request.UserRequest
import br.com.zapzup.manager.api.user.response.UserResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "User", tags = ["User"], description = "User Resources")
@RequestMapping(value = ["/users"])
interface UserApi {

    //TODO("Mapear os erros de cada serviço e acrescentar no @ApiResponses")

    @PostMapping
    @ResponseStatus(CREATED)
    @ResponseBody
    @ApiOperation(value = "Creates a user")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created")
    ])
    fun create(@RequestBody @Validated userRequest: UserRequest): ResponseWrapper<UserResponse>

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
}
