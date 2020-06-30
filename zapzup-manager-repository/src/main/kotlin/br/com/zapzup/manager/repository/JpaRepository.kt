package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
    fun findByIdAndStatus(id: String, status: StatusEnum) : Optional<User>
}

@Repository
interface TokenRepository : JpaRepository<Token, String> {
    fun findByCode(code: String): Token?
    fun findByUserEmail(email: String): Token?
}