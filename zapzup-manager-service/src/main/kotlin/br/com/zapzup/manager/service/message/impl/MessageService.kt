package br.com.zapzup.manager.service.message.impl

import br.com.zapzup.manager.commons.exceptions.UserNotFoundInChatException
import br.com.zapzup.manager.domain.entity.Message
import br.com.zapzup.manager.domain.to.chat.ChatTO
import br.com.zapzup.manager.domain.to.file.FileTO
import br.com.zapzup.manager.domain.to.message.CreateMessageTO
import br.com.zapzup.manager.domain.to.message.MessageTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.MessageRepository
import br.com.zapzup.manager.service.chat.IChatService
import br.com.zapzup.manager.service.chat.mapper.toEntity
import br.com.zapzup.manager.service.file.IFileService
import br.com.zapzup.manager.service.file.mapper.toEntity
import br.com.zapzup.manager.service.message.IMessageService
import br.com.zapzup.manager.service.message.mapper.toTO
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class MessageService(
    private val messageRepository: MessageRepository,
    private val chatService: IChatService,
    private val userService: IUserService,
    private val fileService: IFileService
) : IMessageService {

    override fun save(createMessageTO: CreateMessageTO): MessageTO {
        val userTO = userService.getUserById(createMessageTO.userId)
        val chatTO = chatService.findById(createMessageTO.chatId)
        val fileTO = fileService.saveFile(createMessageTO.file)

        validateUserInChat(userTO, chatTO)

        val message = Message(
            content = createMessageTO.content,
            user = userTO.toEntity(),
            chat = chatTO.toEntity(),
            file = fileTO?.toEntity()
        )

        return messageRepository.save(message).toTO()
    }

    private fun validateUserInChat(userTO: UserTO, chatTO: ChatTO) {
        chatTO.users.firstOrNull { it.id == userTO.id }
            ?: throw UserNotFoundInChatException(userTO.username, chatTO.id)
    }
}