package br.com.zapzup.manager.application.entity

import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_entity")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val username: String? = "",
    val email: String? = "",
    val password: String? = "",
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)