package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.commons.exceptions.EqualPasswordException
import br.com.zapzup.manager.commons.exceptions.InvalidOldPasswordException
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UpdatePasswordTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.impl.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional

class UserServiceTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val passwordEncoder: BCryptPasswordEncoder = mock(BCryptPasswordEncoder::class.java)
    private val userService: IUserService = UserService(userRepository, passwordEncoder)

    @Test
    fun `should create user with success`() {
        val createUserTO = buildCreateUserTO()
        val user = buildUser()

        `when`(userRepository.existsByEmail(createUserTO.email)).thenReturn(false)
        `when`(userRepository.existsByUsername(createUserTO.username)).thenReturn(false)
        `when`(passwordEncoder.encode(createUserTO.password)).thenReturn("F6DS54AGDSA8")
        `when`(userRepository.save(any(User::class.java))).thenAnswer { user }

        val response = userService.create(createUserTO)

        assertThat(response.id).isEqualTo(user.id)
        assertThat(response.email).isEqualTo(user.email)
        assertThat(response.createdAt).isEqualTo(user.createdAt)
    }

    @Test
    fun `should throw an exception when user exists by username`() {
        val createUserTO = buildCreateUserTO()

        `when`(userRepository.existsByUsername(createUserTO.username)).thenReturn(true)

        val exception = assertThrows<UserAlreadyExistsException> { userService.create(createUserTO) }

        assertThat(exception.field).isEqualTo("username")
    }

    @Test
    fun `should throw an exception when user exists by email`() {
        val createUserTO = buildCreateUserTO()

        `when`(userRepository.existsByUsername(createUserTO.username)).thenReturn(false)
        `when`(userRepository.existsByEmail(createUserTO.email)).thenReturn(true)

        val exception = assertThrows<UserAlreadyExistsException> { userService.create(createUserTO) }

        assertThat(exception.field).isEqualTo("email")
    }

    @Test
    fun `should find user by email`() {
        val email = "fulano@gmail.com"
        val user = buildUser()

        `when`(userRepository.findByEmail(email)).thenReturn(user)

        val response = userService.findByEmail(email)

        assertThat(response.id).isEqualTo(user.id)
        assertThat(response.email).isEqualTo(user.email)
    }

    @Test
    fun `should throw an exception when not find user by email`() {
        val email = "fulano@gmail.com"

        `when`(userRepository.findByEmail(email)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { userService.findByEmail(email) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should update password with success`() {
        val id = "USER-ID"
        val passwordEncoded = "FUDSHAFU7284"
        val updatePasswordTO = buildUpdatePasswordTO()
        val user = buildUser()
        val argumentCaptor = ArgumentCaptor.forClass(User::class.java)

        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(updatePasswordTO.oldPassword, user.password)).thenReturn(true)
        `when`(passwordEncoder.encode(updatePasswordTO.newPassword)).thenReturn(passwordEncoded)
        `when`(userRepository.save(any(User::class.java))).thenAnswer { user.copy(password = passwordEncoded) }

        userService.updatePassword(id, updatePasswordTO)

        verify(userRepository, times(1)).save(argumentCaptor.capture())

        assertThat(argumentCaptor.value.id).isEqualTo(user.id)
        assertThat(argumentCaptor.value.password).isEqualTo(passwordEncoded)
    }

    @Test
    fun `should throw an exception when not find user`() {
        val id = "USER-ID"
        val updatePasswordTO = buildUpdatePasswordTO()

        `when`(userRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<UserNotFoundException> { userService.updatePassword(id, updatePasswordTO) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should throw an exception when old password does not match`() {
        val user = buildUser()
        val updatePasswordTO = buildUpdatePasswordTO()

        `when`(userRepository.findById(user.id)).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(updatePasswordTO.oldPassword, user.password)).thenReturn(false)

        val exception = assertThrows<InvalidOldPasswordException> {
            userService.updatePassword(user.id, updatePasswordTO)
        }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should throw an exception when new and old passwords are equal`() {
        val user = buildUser()
        val updatePasswordTO = buildUpdatePasswordTO().copy(newPassword = "123456789")

        `when`(userRepository.findById(user.id)).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches(updatePasswordTO.oldPassword, user.password)).thenReturn(true)

        val exception = assertThrows<EqualPasswordException> {
            userService.updatePassword(user.id, updatePasswordTO)
        }

        assertThat(exception).isNotNull()
    }

    private fun buildUpdatePasswordTO(): UpdatePasswordTO =
        UpdatePasswordTO(
            oldPassword = "123456789",
            newPassword = "789456123"
        )

    private fun buildCreateUserTO(): CreateUserTO =
        CreateUserTO(
            name = "Fulano",
            username = "fulaninho",
            email = "fulano@gmail.com",
            password = "123456789"
        )

    private fun buildUser(): User =
        User(
            id = "USER-ID",
            name = "Fulano",
            username = "fulaninho",
            note = "Suave",
            status = StatusEnum.ACTIVE,
            email = "fulano@gmail.com",
            password = "F6DS54AGDSA8"
        )
}
