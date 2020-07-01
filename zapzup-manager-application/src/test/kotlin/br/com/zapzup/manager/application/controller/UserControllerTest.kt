package br.com.zapzup.manager.application.controller

import br.com.zapzup.manager.api.user.request.CreateUserRequest
import br.com.zapzup.manager.api.user.request.UpdatePasswordRequest
import br.com.zapzup.manager.api.user.request.UpdateUserRequest
import br.com.zapzup.manager.application.config.BasicIntegrationTest
import br.com.zapzup.manager.commons.objectToJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
open class UserControllerTest : BasicIntegrationTest() {

    private val id: String = "USER-ID"
    @Test
    fun `should create user with success`() {
        val createUserRequest = CreateUserRequest(
            name = "Fulano",
            username = "Fulaninho",
            email = "fulano@gmail.com",
            password = "123456789"
        )

        this.mockMvc.perform(post("/users")
            .content(createUserRequest.objectToJson())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.content.id").isNotEmpty)
            .andExpect(jsonPath("$.content.name").value("Fulano"))
            .andExpect(jsonPath("$.content.username").value("Fulaninho"))
            .andExpect(jsonPath("$.content.email").value("fulano@gmail.com"))
    }

    @Test
    @Sql(value = ["/scripts/load-user.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should update user with success`() {
        val updateUserRequest = UpdateUserRequest(
            username = "Fulano2",
            note = "Olá!",
            email = ""
        )

        this.mockMvc.perform(put("/users/$id")
            .content(updateUserRequest.objectToJson())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)

        val user = userRepository.findById(id)
        assertThat(user.isPresent).isTrue()
        assertThat(user.get().updatedAt).isNotNull()
        assertThat(user.get().email).isEqualTo("fulano@gmail.com")
        assertThat(user.get().username).isEqualTo("Fulano2")
        assertThat(user.get().note).isEqualTo("Olá!")
    }

    @Test
    @Sql(value = ["/scripts/load-user.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should update password with success`() {
        val updatePasswordRequest = UpdatePasswordRequest(
            oldPassword = "123456789",
            newPassword = "987654321"
        )

        this.mockMvc.perform(put("/users/$id/update-password")
            .content(updatePasswordRequest.objectToJson())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent)

        val user = userRepository.findById(id)

        assertThat(user.isPresent).isTrue()
        assertThat(this.passwordEncoder.matches(updatePasswordRequest.newPassword, user.get().password)).isTrue()
    }

    @Test
    @Sql(value = ["/scripts/load-user.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should delete user with success`() {
        val id = "USER-ID"

        this.mockMvc.perform(delete("/users/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent)

        val user = userRepository.findById(id)

        assertThat(user.isPresent).isTrue()
        assertThat(user.get().status.name).isEqualTo("INACTIVE")
        assertThat(user.get().deletedAt).isNotNull()
    }
}