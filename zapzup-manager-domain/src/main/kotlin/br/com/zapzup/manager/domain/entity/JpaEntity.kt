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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = User(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    val chat: Chat = Chat()
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
    @OneToOne(targetEntity = File::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    val icon: File? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_chat",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val users: MutableList<User> = mutableListOf()
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

@Entity
@Table(name = "file_entity")
data class File(
    @Id
    val id: String = "IMG-${UUID.randomUUID()}",
    val name: String = "",
    val type: String = "",
    @Column(length = 1000)
    val fileByte: ByteArray? = null,
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (fileByte != null) {
            if (other.fileByte == null) return false
            if (!fileByte.contentEquals(other.fileByte)) return false
        } else if (other.fileByte != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (fileByte?.contentHashCode() ?: 0)
        return result
    }
}