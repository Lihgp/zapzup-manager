package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.entity.File
import br.com.zapzup.manager.domain.entity.Message
import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    @Query(name = "UserEntity.existsByUsername")
    fun existsByUsername(@Param(value = "username") username: String): Boolean

    @Query(name = "UserEntity.existsByEmail")
    fun existsByEmail(@Param(value = "email") email: String): Boolean

    fun findByEmail(@Param(value = "email") email: String): User?

    @Query(name = "UserEntity.findById")
    fun findByIdAndStatus(@Param(value = "id") id: String): User?

    @Query(" FROM User u WHERE ('' = :email or u.email = :email)" +
        " AND ('' = :name or u.name = :name) AND ('' = :username or u.username = :username)")
    fun findByQueryParams(
        @Param("email") email: String,
        @Param("name") name: String,
        @Param("username") username: String,
        pageable: Pageable
    ): Page<User>
}

@Repository
interface TokenRepository : JpaRepository<Token, String> {
    fun findByCode(code: String): Token?
    fun findByUserEmail(email: String): Token?
}

@Repository
interface ChatRepository : JpaRepository<Chat, String>

@Repository
interface FileRepository : JpaRepository<File, String>

@Repository
interface MessageRepository : JpaRepository<Message, String>