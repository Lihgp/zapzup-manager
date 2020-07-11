package br.com.zapzup.manager.service.chat

import br.com.zapzup.manager.commons.exceptions.DuplicatedIdException
import br.com.zapzup.manager.domain.entity.Chat
import br.com.zapzup.manager.domain.to.chat.CreateGroupChatTO
import br.com.zapzup.manager.domain.to.chat.CreatePrivateChatTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.ChatRepository
import br.com.zapzup.manager.service.chat.impl.ChatService
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

class ChatServiceTest {
    private val chatRepository: ChatRepository = mock(ChatRepository::class.java)
    private val fileService: IFileService = mock(IFileService::class.java)
    private val userService: IUserService = mock(IUserService::class.java)
    private val chatService: IChatService = ChatService(chatRepository, fileService, userService)

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
                name = createGroupChatTO.chatName,
                description = createGroupChatTO.chatDescription,
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

    private fun buildCreateGroupChatTO(memberId: String): CreateGroupChatTO =
        CreateGroupChatTO(
            chatName = "Chat name",
            chatDescription = "Chat description",
            creatorUserId = creatorUserId,
            members = listOf(CreateGroupChatTO.UserIdTO(memberId))
        )

    private fun buildCreatePrivateChatTO(): CreatePrivateChatTO =
        CreatePrivateChatTO(
            creatorUserId = creatorUserId,
            memberId = "USER-ID"
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