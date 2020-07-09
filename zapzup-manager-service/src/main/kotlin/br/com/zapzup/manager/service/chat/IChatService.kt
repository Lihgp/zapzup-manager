package br.com.zapzup.manager.service.chat

import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreateChatTO

interface IChatService {

    fun create(createChatTO: CreateChatTO): ChatTO
}