package br.com.zapzup.manager.api.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.response.UserResponse
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

interface UserApi {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun getUsers(
        @RequestParam(name = "email", required = false) email: String,
        @RequestParam(name = "username", required = false) username: String,
        @RequestParam(name = "name", required = false) name: String,
        @RequestParam(name = "page", defaultValue = "1", required = false) page: Int,
        @RequestParam(name = "limit", defaultValue = "10", required = false) limit: Int
    ): ResponseWrapper<List<UserResponse>>
}