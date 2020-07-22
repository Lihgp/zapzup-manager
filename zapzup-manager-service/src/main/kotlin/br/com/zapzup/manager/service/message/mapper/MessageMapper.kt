package br.com.zapzup.manager.service.message.mapper

import br.com.zapzup.manager.domain.entity.Message
import br.com.zapzup.manager.domain.to.message.MessageTO
import br.com.zapzup.manager.service.file.mapper.toTO

fun Message.toTO() = messageTO(message = this)

fun messageTO(message: Message): MessageTO =
    MessageTO(
        id = message.id,
        sender = message.user.username,
        content = message.content,
        file = message.file?.toTO(),
        createdAt = message.createdAt
    )