package br.com.zapzup.manager.service.message

import br.com.zapzup.manager.domain.to.message.CreateMessageTO
import br.com.zapzup.manager.domain.to.message.MessageTO

interface IMessageService {
    fun save(createMessageTO: CreateMessageTO): MessageTO
}