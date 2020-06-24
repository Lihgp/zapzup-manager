package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE (?1 = '' or ?1 = u.email)" +
        " AND (?2 = '' or ?2 = u.name)" +
        " AND (?3 = '' or ?3 = u.username)")
    fun findByQueryParams(
        email: String,
        name: String,
        username: String,
        pageable: Pageable
    ): Page<Any>
}