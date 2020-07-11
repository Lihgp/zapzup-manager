package br.com.zapzup.manager.domain.to.file

import java.time.OffsetDateTime

data class FileTO(
    val id: String,
    val name: String,
    val type: String,
    val fileByte: ByteArray?,
    val createdAt: OffsetDateTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileTO

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (fileByte != null) {
            if (other.fileByte == null) return false
            if (!fileByte.contentEquals(other.fileByte)) return false
        } else if (other.fileByte != null) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (fileByte?.contentHashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        return result
    }
}