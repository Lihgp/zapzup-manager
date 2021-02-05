package br.com.zapzup.manager.service.auth.impl

import br.com.zapzup.manager.commons.utils.JWTUtil
import br.com.zapzup.manager.domain.to.auth.AuthTokenTO
import br.com.zapzup.manager.service.auth.IAuthService
import br.com.zapzup.manager.service.auth.UserDetailsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) : IAuthService {
    override fun generateToken(authTokenTO: AuthTokenTO): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authTokenTO.email, authTokenTO.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val user = userDetailsService.loadUserByUsername(authTokenTO.email)

        return "Bearer ${this.jwtUtil.generateToken(user.username)}"
    }
}