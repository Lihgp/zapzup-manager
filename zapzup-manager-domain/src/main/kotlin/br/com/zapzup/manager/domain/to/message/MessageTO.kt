package br.com.zapzup.manager.domain.to.message

import br.com.zapzup.manager.domain.to.file.FileTO
import java.time.OffsetDateTime

data class MessageTO(
    val id: String,
    val sender: String,
    val content: String,
    val file: FileTO?,
    val createdAt: OffsetDateTime
)