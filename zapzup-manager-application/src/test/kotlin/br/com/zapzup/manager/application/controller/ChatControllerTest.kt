package br.com.zapzup.manager.application.controller

import br.com.zapzup.manager.api.chat.request.CreateGroupChatRequest
import br.com.zapzup.manager.api.chat.request.CreatePrivateChatRequest
import br.com.zapzup.manager.application.config.BasicIntegrationTest
import br.com.zapzup.manager.commons.objectToJson
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@Transactional
open class ChatControllerTest : BasicIntegrationTest() {

    @Test
    @Sql(value = ["/scripts/load-users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should create private chat with success`() {
        val createPrivateChatRequest = CreatePrivateChatRequest(
            creatorUserId = "CREATOR-USER-ID",
            memberId = "MEMBER-USER-ID"
        )

        this.mockMvc.perform(post("/chats/private")
            .content(createPrivateChatRequest.objectToJson())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.content.id").isNotEmpty)
            .andExpect(jsonPath("$.content.createdBy").value("Sicraninho"))
            .andExpect(jsonPath("$.content.users.[0].id").value("CREATOR-USER-ID"))
            .andExpect(jsonPath("$.content.users.[1].id").value("MEMBER-USER-ID"))
    }

    @Test
    @Sql(value = ["/scripts/load-users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should create group chat with success`() {
        val createGroupChatRequest = CreateGroupChatRequest(
            name = "Chat name",
            description = "chat description",
            creatorUserId = "CREATOR-USER-ID",
            members = listOf(CreateGroupChatRequest.UserId("MEMBER-USER-ID"))
        )

        val file = MockMultipartFile(
            "groupIcon", "filename.txt", "text/plain", "file data".toByteArray()
        )

        this.mockMvc.perform(multipart("/chats/group")
            .file(file)
            .param("createGroupChatRequest", createGroupChatRequest.objectToJson())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.content.id").isNotEmpty)
            .andExpect(jsonPath("$.content.name").value("Chat name"))
            .andExpect(jsonPath("$.content.description").value("chat description"))
            .andExpect(jsonPath("$.content.createdBy").value("Sicraninho"))
            .andExpect(jsonPath("$.content.users.[0].id").value("CREATOR-USER-ID"))
            .andExpect(jsonPath("$.content.users.[1].id").value("MEMBER-USER-ID"))
    }
}