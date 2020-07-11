package br.com.zapzup.manager.service.chat

import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreateChatTO
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import org.springframework.web.multipart.MultipartFile

interface IChatService {

    fun create(createChatTO: CreateChatTO): ChatTO

    fun createGroupChat(createGroupChatTO: CreateGroupChatTO, groupIcon: MultipartFile): ChatTO
}