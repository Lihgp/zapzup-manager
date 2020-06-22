package br.com.zapzup.manager.api.user

import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.user.request.UserRequest
import br.com.zapzup.manager.api.user.response.UserResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping(value = ["/users"])
interface UserApi {

    @PostMapping
    @ResponseStatus(CREATED)
    @ResponseBody
    fun create(@RequestBody @Validated userRequest: UserRequest): ResponseWrapper<UserResponse>
}