package br.com.zapzup.manager.application.config.utils

import br.com.zapzup.manager.commons.utils.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil,
    private val userDetailService: UserDetailsService
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            val auth = getAuthentication(authorizationHeader.substring(7))
            SecurityContextHolder.getContext().authentication = auth
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (jwtUtil.isTokenValid(token)) {
            val username = jwtUtil.getUserName(token)
            val userDetail = userDetailService.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
        }
        return null
    }
}