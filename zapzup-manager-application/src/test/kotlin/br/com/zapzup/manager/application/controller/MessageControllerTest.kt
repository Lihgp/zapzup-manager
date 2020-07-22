package br.com.zapzup.manager.application.controller

import br.com.zapzup.manager.api.message.request.CreateMessageRequest
import br.com.zapzup.manager.application.config.BasicIntegrationTest
import br.com.zapzup.manager.commons.objectToJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
open class MessageControllerTest : BasicIntegrationTest() {

    @Test
    @SqlGroup(
        Sql(value = ["/scripts/load-users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = ["/scripts/load-chat.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    )
    fun `should send message with success`() {
        val createMessageRequest = CreateMessageRequest(
            userId = "USER-ID",
            chatId = "CHAT-ID",
            content = "content"
        )

        this.mockMvc.perform(multipart("/messages")
            .param("createMessageRequest", createMessageRequest.objectToJson())
            .contentType(MediaType.MULTIPART_FORM_DATA))
            .andDo(print())
            .andExpect(status().isOk)

        val messages = messageRepository.findAll()
        val chat = chatRepository.findById("CHAT-ID")

        assertThat(messages).isNotEmpty
        assertThat(chat.isPresent).isTrue()
        assertThat(chat.get().lastMessageSentAt).isNotNull()
        assertThat(messages[0].content).isEqualTo(createMessageRequest.content)
    }
}