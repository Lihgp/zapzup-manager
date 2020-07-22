package br.com.zapzup.manager.service.chat

import br.com.zapzup.manager.commons.exceptions.ChatNotFoundException
import br.com.zapzup.manager.commons.exceptions.DuplicatedIdException
import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.ChatStatusEnum
import br.com.zapzup.manager.domain.enums.UserStatusEnum
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import br.com.zapzup.manager.domain.to.chat.CreatePrivateChatTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.ChatRepository
import br.com.zapzup.manager.service.chat.impl.ChatService
import br.com.zapzup.manager.service.chat.mapper.toTOList
import br.com.zapzup.manager.service.file.IFileService
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.messaging.simp.SimpMessageSendingOperations
import java.time.OffsetDateTime
import java.util.Optional

class ChatServiceTest {
    private val chatRepository: ChatRepository = mock(ChatRepository::class.java)
    private val fileService: IFileService = mock(IFileService::class.java)
    private val userService: IUserService = mock(IUserService::class.java)
    private val messagingTemplate: SimpMessageSendingOperations = mock(SimpMessageSendingOperations::class.java)
    private val chatService: IChatService = ChatService(chatRepository, fileService, userService, messagingTemplate)

    private val creatorUserId: String = "USER-ID-CREATOR"

    @Test
    fun `should create private chat with success`() {
        val memberId = "USER-ID"
        val createPrivateChatTO = buildCreatePrivateChatTO()
        val creatorTO = buildUserTO(creatorUserId, "fulaninho", "fulano@gmail.com")
        val memberTO = buildUserTO(memberId, "sicraninho", "sicrano@gmail.com")
        val argumentCaptor = ArgumentCaptor.forClass(Chat::class.java)

        `when`(userService.getUserById(creatorUserId)).thenReturn(creatorTO)
        `when`(userService.getUserById(memberId)).thenReturn(memberTO)
        `when`(chatRepository.save(any(Chat::class.java))).thenAnswer {
            Chat(
                id = "CHAT-ID",
                createdBy = creatorTO.username,
                users = mutableListOf(creatorTO.toEntity(), memberTO.toEntity())
            )
        }

        val response = chatService.createPrivateChat(createPrivateChatTO)

        verify(chatRepository, times(1)).save(argumentCaptor.capture())

        assertThat(response.id).isEqualTo("CHAT-ID")
        assertThat(response.users[0].id).isEqualTo(creatorUserId)
        assertThat(response.users[1].id).isEqualTo(memberId)
    }

    @Test
    fun `should throw an exception when has equals id's`() {
        val memberId = creatorUserId
        val createPrivateChatTO = buildCreatePrivateChatTO().copy(memberId = memberId)

        val exception = assertThrows<DuplicatedIdException> { chatService.createPrivateChat(createPrivateChatTO) }

        assertThat(exception).isNotNull()
        assertThat(exception.id).isEqualTo(memberId)
    }

    @Test
    fun `should create group chat with success`() {
        val memberId = "USER-ID"
        val createGroupChatTO = buildCreateGroupChatTO(memberId)
        val creatorTO = buildUserTO(creatorUserId, "fulaninho", "fulano@gmail.com")
        val memberTO = buildUserTO(memberId, "sicraninho", "sicrano@gmail.com")
        val argumentCaptor = ArgumentCaptor.forClass(Chat::class.java)

        `when`(userService.getUserById(creatorUserId)).thenReturn(creatorTO)
        `when`(userService.getUserById(memberId)).thenReturn(memberTO)
        `when`(chatRepository.save(any(Chat::class.java))).thenAnswer {
            Chat(
                id = "CHAT-ID",
                name = createGroupChatTO.name,
                description = createGroupChatTO.description,
                createdBy = creatorTO.username,
                users = mutableListOf(creatorTO.toEntity(), memberTO.toEntity())
            )
        }

        val response = chatService.createGroupChat(createGroupChatTO, null)

        verify(chatRepository, times(1)).save(argumentCaptor.capture())

        assertThat(response.id).isEqualTo("CHAT-ID")
        assertThat(response.users[0].id).isEqualTo(creatorUserId)
        assertThat(response.users[1].id).isEqualTo(memberId)
    }

    @Test
    fun `should throw an exception when has duplicated id's`() {
        val memberId = creatorUserId
        val createGroupChatTO = buildCreateGroupChatTO(memberId)
        val creatorTO = buildUserTO(creatorUserId, "fulaninho", "fulano@gmail.com")

        `when`(userService.getUserById(creatorUserId)).thenReturn(creatorTO)

        val exception = assertThrows<DuplicatedIdException> {
            chatService.createGroupChat(createGroupChatTO, null)
        }

        assertThat(exception).isNotNull()
        assertThat(exception.id).isEqualTo(memberId)
    }

    @Test
    fun `should find by id with success`() {
        val user = buildUser(creatorUserId, "sicraninho", "sicrano@gmail.com")
        val chat = buildChat(user)

        `when`(chatRepository.findById(chat.id)).thenReturn(Optional.of(chat))

        val response = chatService.findById(chat.id)

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(chat.id)
    }

    @Test
    fun `should throw an exception when not find chat by id`() {
        val chatId = "CHAT-ID"

        `when`(chatRepository.findById(chatId)).thenReturn(Optional.empty())

        val exception = assertThrows<ChatNotFoundException> { chatService.findById(chatId) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should update chat with last message sent`() {
        val user = buildUser(creatorUserId, "sicraninho", "sicrano@gmail.com")
        val chat = buildChat(user)
        val argumentCaptor = ArgumentCaptor.forClass(Chat::class.java)

        `when`(chatRepository.findById(chat.id)).thenReturn(Optional.of(chat))
        `when`(chatRepository.save(any(Chat::class.java))).thenAnswer {
            chat.copy(lastMessageSentAt = OffsetDateTime.now())
        }

        chatService.updateLastMessageSent(chat.id)

        verify(chatRepository, times(1)).save(argumentCaptor.capture())

        assertThat(argumentCaptor.value).isNotNull
        assertThat(argumentCaptor.value.lastMessageSentAt).isBefore(OffsetDateTime.now())
        assertThat(argumentCaptor.value.status).isEqualTo(ChatStatusEnum.ACTIVE)
    }

    @Test
    fun `should send to users chat ordered`() {
        val user = buildUser(creatorUserId, "sicraninho", "sicrano@gmail.com")
        val chat = buildChat(user)

        `when`(chatRepository.findById(chat.id)).thenReturn(Optional.of(chat))
        `when`(chatRepository.findAllByUserId(user.id)).thenReturn(listOf(chat))

        chatService.sendToUsersChatsOrderedByLastMessageSent(chat.id)

        verify(messagingTemplate, times(1))
            .convertAndSend("/topic/chats/${user.id}", listOf(chat).toTOList())
    }

    private fun buildCreateGroupChatTO(memberId: String): CreateGroupChatTO =
        CreateGroupChatTO(
            name = "Chat name",
            description = "Chat description",
            creatorUserId = creatorUserId,
            members = listOf(CreateGroupChatTO.UserIdTO(memberId))
        )

    private fun buildCreatePrivateChatTO(): CreatePrivateChatTO =
        CreatePrivateChatTO(
            creatorUserId = creatorUserId,
            memberId = "USER-ID"
        )

    private fun buildChat(user: User): Chat =
        Chat(
            id = "CHAT-ID",
            status = ChatStatusEnum.ACTIVE,
            createdBy = creatorUserId,
            users = mutableListOf(user)
        )

    private fun buildUser(id: String, username: String, email: String): User =
        User(
            id = id,
            name = "Fulano",
            username = username,
            email = email,
            status = UserStatusEnum.ACTIVE
        )

    private fun buildUserTO(id: String, username: String, email: String): UserTO =
        UserTO(
            id = id,
            name = "Fulano",
            username = username,
            email = email,
            status = "ACTIVE"
        )
}