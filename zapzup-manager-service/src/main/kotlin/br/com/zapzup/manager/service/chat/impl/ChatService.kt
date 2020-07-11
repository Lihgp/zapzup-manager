package br.com.zapzup.manager.service.chat.impl

import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.ChatStatusEnum
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.chat.CreateChatTO
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import br.com.zapzup.manager.repository.ChatRepository
import br.com.zapzup.manager.service.chat.IChatService
import br.com.zapzup.manager.service.chat.mapper.toTO
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val userService: IUserService
) : IChatService {

    override fun create(createChatTO: CreateChatTO): ChatTO {
        val userTO = userService.getUserById(createChatTO.userId)

        val chat = chatRepository.save(
            Chat(createdBy = userTO.name, users = mutableListOf(userTO.toEntity()))
        )

        return chat.toTO()
    }

    override fun createGroupChat(createGroupChatTO: CreateGroupChatTO): ChatTO {
        val members: MutableList<User> = mutableListOf()
        val creatorUserTO = userService.getUserById(createGroupChatTO.creatorUserId)

        members.add(creatorUserTO.toEntity())

        createGroupChatTO.members.forEach {
            val userTO = userService.getUserById(it.id)

            members.add(userTO.toEntity())
        }

        val chat = chatRepository.save(
            Chat(
                name = createGroupChatTO.chatName,
                description = createGroupChatTO.chatDescription,
                createdBy = creatorUserTO.username,
                status = ChatStatusEnum.ACTIVE,
                users = members
            )
        )

        return chat.toTO()
    }
}