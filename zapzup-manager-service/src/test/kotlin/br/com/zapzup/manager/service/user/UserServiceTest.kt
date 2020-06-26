package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.impl.UserService
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest {

    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val service = UserService(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder
    )

    @Test
    fun `should get all users`() {
        val filter = createFilter()
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findAll(pageable))
            .thenReturn(createUserPage(pageable = pageable))
        val users = service.getUsers(filter = filter)

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

        val users = service.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 2)
        Assert.assertEquals(users.content[0].name, "user1")
        Assert.assertEquals(users.content[0].name, users.content[1].name)
    }

    @Test
    fun `should not find user`() {
        val filter = createFilter()
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findAll(pageable)).thenReturn(PageImpl<User>(emptyList()))

        val users = service.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 0)
    }

    @Test
    fun `should not find any user filtered`() {
        val filter = createFilter(name = "user1")
        val pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        Mockito.`when`(userRepository.findByQueryParams(
            email = filter.email, name = filter.name, username = filter.username, pageable = pageable)
        ).thenReturn(PageImpl<User>(emptyList()))

        val users = service.getUsers(filter = filter)

        Assert.assertEquals(users.content.size, 0)
    }

    private fun createFilter(
        email: String = "",
        username: String = "",
        name: String = "",
        page: Int = 0,
        limit: Int = 10
    ) : GetUsersFilter {
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
}