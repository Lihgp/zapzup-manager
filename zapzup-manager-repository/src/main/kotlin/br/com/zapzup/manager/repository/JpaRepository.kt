package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.ResetPasswordToken
import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User
}

@Repository
interface ResetPasswordTokenRepository : JpaRepository<ResetPasswordToken, String> {
    fun findByToken(token: String): ResetPasswordToken?
}