package br.com.zapzup.manager.domain.entity

import br.com.zapzup.manager.domain.enums.ChatStatusEnum
import br.com.zapzup.manager.domain.enums.StatusEnum
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "user_entity")
data class User(
    @Id
    val id: String = "USER-${UUID.randomUUID()}",
    val name: String = "",
    @Column(unique = true)
    val username: String = "",
    val note: String = "Hello! I'm using ZapZup.",
    @Enumerated(EnumType.STRING)
    val status: StatusEnum = StatusEnum.ACTIVE,
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
    val id: String = "CHAT-${UUID.randomUUID()}",
    val name: String = "",
    val description: String = "",
    @Enumerated(value = EnumType.STRING)
    val status: ChatStatusEnum = ChatStatusEnum.CREATED,
    val createdBy: String = "",
    val updatedBy: String = "",
    val deletedBy: String = "",
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
    @ManyToMany
    @JoinTable(
        name = "user_chat",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val users: MutableList<User> = mutableListOf(),
    @ManyToMany
    @JoinTable(
        name = "message_chat",
        joinColumns = [JoinColumn(name = "message_id")],
        inverseJoinColumns = [JoinColumn(name = "chat_id")]
    )
    val messages: MutableList<Message> = mutableListOf()
)

@Entity
@Table(name = "token_entity")
data class Token(
    @Id
    val id: String = "TOKEN-${UUID.randomUUID()}",
    @Column(unique = true)
    val code: String = UUID.randomUUID().toString(),
    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User = User(),
    val expirationDate: OffsetDateTime? = null
)