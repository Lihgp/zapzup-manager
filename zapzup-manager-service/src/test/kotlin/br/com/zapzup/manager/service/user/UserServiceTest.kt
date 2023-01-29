package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.commons.exceptions.EqualPasswordException
import br.com.zapzup.manager.commons.exceptions.InvalidOldPasswordException
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UpdatePasswordTO
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.auth.impl.AuthService
import br.com.zapzup.manager.service.user.impl.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserServiceTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val passwordEncoder: BCryptPasswordEncoder = mock(BCryptPasswordEncoder::class.java)
    private val authService: AuthService = mock(AuthService::class.java)

    private val userService: IUserService = UserService(userRepository, passwordEncoder, authService)

    private val id: String = "USER-ID"
    private val username: String = "fulaninho"
    private val newUsername: String = "fulaninho2"
    private val email: String = "fulano@gmail.com"
    private val note: String = "Suave"
    private val newNote: String = "Suavao"
    private val name: String = "Fulano"
    private val password: String = "F6DS54AGDSA8"
    private val passwordEncoded: String = "FUDSHAFU7284"

    @Test
    fun `should throw an exception when not find user - update`() {
        val updateUserTO = buildUpdateUserTO()

        `when`(userRepository.findByIdAndStatus(id)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { userService.update(updateUserTO = updateUserTO) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should update user with success`() {
        val updateUserTO = buildUpdateUserTO()
        val user = buildUser()
        val argumentCaptor = ArgumentCaptor.forClass(User::class.java)

        `when`(userRepository.findByIdAndStatus(id)).thenReturn(user)
        `when`(userRepository.save(any(User::class.java))).thenAnswer { user }

        userService.update(updateUserTO = updateUserTO)

        verify(userRepository, times(1)).save(argumentCaptor.capture())

        assertThat(argumentCaptor.value.id).isEqualTo(user.id)
        assertThat(argumentCaptor.value.email).isEqualTo(email)
        assertThat(argumentCaptor.value.note).isEqualTo(newNote)
        assertThat(argumentCaptor.value.username).isEqualTo(newUsername)
        assertThat(argumentCaptor.value.updatedAt).isNotNull()
    }

    @Test
    fun `should create user with success`() {
        val createUserTO = buildCreateUserTO()
        val user = buildUser()

        `when`(userRepository.existsByEmail(createUserTO.email)).thenReturn(false)
        `when`(userRepository.existsByUsername(createUserTO.username)).thenReturn(false)
        `when`(passwordEncoder.encode(createUserTO.password)).thenReturn(password)
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
        val user = buildUser()

        `when`(userRepository.findByEmail(email)).thenReturn(user)

        val response = userService.getByEmail(email)

        assertThat(response.id).isEqualTo(user.id)
        assertThat(response.email).isEqualTo(user.email)
    }

    @Test
    fun `should throw an exception when not find user by email`() {
        `when`(userRepository.findByEmail(email)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { userService.getByEmail(email) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should update password with success`() {
        val updatePasswordTO = buildUpdatePasswordTO()
        val user = buildUser()
        val argumentCaptor = ArgumentCaptor.forClass(User::class.java)

        `when`(userRepository.findByIdAndStatus(id)).thenReturn(user)
        `when`(passwordEncoder.matches(updatePasswordTO.oldPassword, user.password)).thenReturn(true)
        `when`(passwordEncoder.encode(updatePasswordTO.newPassword)).thenReturn(passwordEncoded)
        `when`(userRepository.save(any(User::class.java))).thenAnswer { user.copy(password = passwordEncoded) }

        userService.updatePassword(id, updatePasswordTO)

        verify(userRepository, times(1)).save(argumentCaptor.capture())

        assertThat(argumentCaptor.value.id).isEqualTo(user.id)
        assertThat(argumentCaptor.value.password).isEqualTo(passwordEncoded)
    }

    @Test
    fun `should throw an exception when not find user - update password`() {
        val updatePasswordTO = buildUpdatePasswordTO()

        `when`(userRepository.findByIdAndStatus(id)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { userService.updatePassword(id, updatePasswordTO) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should throw an exception when old password does not match`() {
        val user = buildUser()
        val updatePasswordTO = buildUpdatePasswordTO()

        `when`(userRepository.findByIdAndStatus(user.id)).thenReturn(user)
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

        `when`(userRepository.findByIdAndStatus(user.id)).thenReturn(user)
        `when`(passwordEncoder.matches(updatePasswordTO.oldPassword, user.password)).thenReturn(true)

        val exception = assertThrows<EqualPasswordException> {
            userService.updatePassword(user.id, updatePasswordTO)
        }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should delete user with success`() {
        val user = buildUser()
        val argumentCaptor = ArgumentCaptor.forClass(User::class.java)

        `when`(userRepository.findByIdAndStatus(user.id)).thenReturn(user)

        userService.delete(id = user.id)

        verify(userRepository, times(1)).save(argumentCaptor.capture())

        assertThat(argumentCaptor.value.status).isEqualTo(StatusEnum.INACTIVE)
        assertThat(argumentCaptor.value.deletedAt).isNotNull()
    }

    @Test
    fun `should throw an exception when not find user - delete`() {
        `when`(userRepository.findByIdAndStatus(id)).thenReturn(null)

        val exception = assertThrows<UserNotFoundException> { userService.delete(id = id) }

        assertThat(exception).isNotNull()
    }

    @Test
    fun `should get all users`() {
        val filter = createFilter()
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findAll(pageable))
            .thenReturn(createUserPage(pageable = pageable))
        val users = userService.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 2)
        Assert.assertEquals(users.content[0].id, "user1")
        Assert.assertEquals(users.content[1].id, "user2")
        Assert.assertEquals(users.content[0].name, "user1")
        Assert.assertEquals(users.content[1].name, "user2")
    }

    @Test
    fun `should get users filtered by name`() {
        val filter = createFilter(name = "user1")
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findByQueryParams
        (
            email = filter.email, name = filter.name, username = filter.username, pageable = pageable)
        ).thenReturn(createUserPage(pageable = pageable, filtered = true))

        val users = userService.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 2)
        Assert.assertEquals(users.content[0].name, "user1")
        Assert.assertEquals(users.content[0].name, users.content[1].name)
    }

    @Test
    fun `should not find user`() {
        val filter = createFilter()
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findAll(pageable)).thenReturn(PageImpl<User>(emptyList()))

        val users = userService.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 0)
    }

    @Test
    fun `should not find any user filtered`() {
        val filter = createFilter(name = "user1")
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findByQueryParams(
            email = filter.email, name = filter.name, username = filter.username, pageable = pageable)
        ).thenReturn(PageImpl<User>(emptyList()))

        val users = userService.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 0)
    }

    private fun createFilter(
        email: String = "",
        username: String = "",
        name: String = "",
        page: Int = 0,
        limit: Int = 10
    ): GetUsersFilter {
        return GetUsersFilter(
            email = email,
            username = username,
            name = name,
            page = page,
            limit = limit
        )
    }

    private fun createUserPage(pageable: Pageable, filtered: Boolean = false): Page<User> {
        val name1 = "user1"
        var name2 = "user2"
        if (filtered) name2 = name1
        val users = listOf(
            User(
                id = "user1",
                name = name1,
                username = "user1",
                email = "user1@zapzup.com",
                password = "user"
            ),
            User(
                id = "user2",
                name = name2,
                username = "user2",
                email = "user2@zapzup.com",
                password = "user"
            )
        )

        return PageImpl<User>(users, pageable, users.size.toLong())
    }

    private fun buildUpdatePasswordTO(): UpdatePasswordTO =
        UpdatePasswordTO(
            oldPassword = "123456789",
            newPassword = "789456123"
        )

    private fun buildUpdateUserTO(): UpdateUserTO =
        UpdateUserTO(
            id = id,
            username = newUsername,
            note = newNote,
            email = ""
        )

    private fun buildCreateUserTO(): CreateUserTO =
        CreateUserTO(
            name = name,
            username = username,
            email = email,
            password = "123456789"
        )

    private fun buildUser(): User =
        User(
            id = id,
            name = name,
            username = username,
            note = note,
            status = StatusEnum.ACTIVE,
            email = email,
            password = password
        )
}
