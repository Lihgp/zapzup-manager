package br.com.zapzup.manager.service.chat

import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreatePrivateChatTO
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import org.springframework.web.multipart.MultipartFile

interface IChatService {

    fun createPrivateChat(createPrivateChatTO: CreatePrivateChatTO): ChatTO

    fun createGroupChat(createGroupChatTO: CreateGroupChatTO, groupIcon: MultipartFile): ChatTO
}