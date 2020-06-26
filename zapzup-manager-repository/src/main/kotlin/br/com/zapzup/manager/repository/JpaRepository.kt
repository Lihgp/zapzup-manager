package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    @Query(" FROM User u WHERE ('' = :email or u.email = :email)" +
        " AND ('' = :name or u.name = :name) AND ('' = :username or u.username = :username)")
    fun findByQueryParams(
        @Param("email") email: String,
        @Param("name") name: String,
        @Param("username") username: String,
        pageable: Pageable
    ): Page<User>
}