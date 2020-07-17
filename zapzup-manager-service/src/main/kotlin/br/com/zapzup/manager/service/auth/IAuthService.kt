package br.com.zapzup.manager.service.auth

import br.com.zapzup.manager.domain.to.auth.AuthTokenTO

interface IAuthService {

    fun generateToken(authTokenTO: AuthTokenTO): String
}