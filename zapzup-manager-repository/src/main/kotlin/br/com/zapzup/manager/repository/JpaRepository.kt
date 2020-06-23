package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    //TODO: Fazer a query. OBS: será query nativa?
    fun findByQueryParams(
        email: String,
        name: String,
        username: String,
        offset: Int?,
        limit: Int?
    ): List<Any>
}