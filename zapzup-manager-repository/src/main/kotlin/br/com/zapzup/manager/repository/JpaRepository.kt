package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    @Query(name = "UserEntity.existsByUsername", nativeQuery = true)
    fun existsByUsername(@Param(value = "username") username: String): Boolean

    @Query(name = "UserEntity.existsByEmail", nativeQuery = true)
    fun existsByEmail(@Param(value = "email") email: String): Boolean

    fun findByEmail(@Param(value = "email") email: String): User?

    @Query(name = "UserEntity.findById", nativeQuery = true)
    fun findByIdAndStatusActive(@Param(value = "id") id: String): User?
}

@Repository
interface TokenRepository : JpaRepository<Token, String> {
    fun findByCode(code: String): Token?
    fun findByUserEmail(email: String): Token?
}