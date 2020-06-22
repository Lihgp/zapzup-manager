package br.com.zapzup.manager.domain.entity

import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user_entity")
data class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String = "USER-${UUID.randomUUID()}",
    @Column(unique = true)
    val username: String = "",
    val status: String = "",
    @Column(unique = true)
    val email: String = "",
    val password: String = "",
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
)

@Entity
@Table(name = "message_entity")
data class Message(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String = "MES-${UUID.randomUUID()}",
    val content: String = "",
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User = User()
)

@Entity
@Table(name = "chat_entity")
data class Chat(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String = "CHAT-${UUID.randomUUID()}",
    val name: String = "",
    val description: String = "",
    val createdBy: String = "",
    val updatedBy: String = "",
    val deletedBy: String = "",
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
    @ManyToMany
    @JoinTable(
        name = "user_chat",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "chat_id")]
    )
    val users: List<User> = listOf(),
    @ManyToMany
    @JoinTable(
        name = "message_chat",
        joinColumns = [JoinColumn(name = "message_id")],
        inverseJoinColumns = [JoinColumn(name = "chat_id")]
    )
    val messages: List<Message> = listOf()
)
