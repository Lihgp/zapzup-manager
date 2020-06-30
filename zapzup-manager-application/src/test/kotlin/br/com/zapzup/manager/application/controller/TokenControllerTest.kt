package br.com.zapzup.manager.application.controller

import br.com.zapzup.manager.api.token.request.GenerateTokenRequest
import br.com.zapzup.manager.application.config.BasicIntegrationTest
import br.com.zapzup.manager.commons.objectToJson
import br.com.zapzup.manager.service.email.IEmailService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
open class TokenControllerTest : BasicIntegrationTest() {

    @MockBean
    private lateinit var emailService: IEmailService

    @Test
    @Sql(value = ["/scripts/load-user.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should generate token with success`() {
        val generatePasswordRequest = GenerateTokenRequest(
            email = "fulano@gmail.com"
        )

        this.mockMvc.perform(post("/tokens/reset-password")
            .content(generatePasswordRequest.objectToJson())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent)

        val token = this.tokenRepository.findByUserEmail(generatePasswordRequest.email)

        assertThat(token).isNotNull
        assertThat(token?.user?.id).isEqualTo("USER-ID")
    }

    @Test
    @SqlGroup(
        Sql(value = ["/scripts/load-user.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = ["/scripts/load-token.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    )
    fun `should validate token with success`() {
        val code = "89SHAF89-FHD8SAYF"

        this.mockMvc.perform(get("/tokens/validate")
            .header("x-code", code)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent)

        val token = this.tokenRepository.findByCode(code)

        assertThat(token).isNull()
    }
}