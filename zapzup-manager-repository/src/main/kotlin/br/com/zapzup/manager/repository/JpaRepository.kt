package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}

@Repository
interface TokenRepository : JpaRepository<Token, String> {
    fun findByCode(code: String): Token?
    fun findByUserEmail(email: String): Token?
}