package br.com.zapzup.manager.service.user.impl

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
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : IUserService {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun create(createUserTO: CreateUserTO): UserTO {
        log.info("CreateUserTO: ${createUserTO.copy(password = "")}")

        when {
            userRepository.existsByUsername(createUserTO.username) -> throw UserAlreadyExistsException("username")
            userRepository.existsByEmail(createUserTO.email) -> throw UserAlreadyExistsException("email")
        }

        val userInactive = userRepository.findByEmail(createUserTO.email)
        val encryptedPassword = passwordEncoder.encode(createUserTO.password)

        if (userInactive != null) {
            log.info("UserInactive: ${userInactive.copy(password = "")}")

            return userRepository.save(
                createUserTO.toEntity().copy(
                    id = userInactive.id,
                    password = encryptedPassword
                )
            ).toTO()
        }

        val user = createUserTO.toEntity().copy(password = encryptedPassword)

        return userRepository.save(user).toTO()
    }

    override fun getUsers(filter: GetUsersFilter): Page<UserTO> {

        filter.validateFilter()
        val pageagle: Pageable = PageRequest.of(filter.page, filter.limit, Sort.by("name"))

        return if (filter.hasFilter()) userRepository.findByQueryParams(
            filter.email,
            filter.name,
            filter.username,
            pageagle
        ).map { user -> user.toTO() }
        else userRepository.findAll(pageagle).map { user -> user.toTO() }
    }

    override fun getUserById(userId: String): UserTO = getUserActive(userId).toTO()

    override fun getByEmail(email: String): UserTO {
        log.info("UserEmail: $email")

        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()

        log.info("User found by email: ${user.copy(password = "")}")

        if (user.status == StatusEnum.INACTIVE) throw UserNotFoundException()

        return user.toTO()
    }

    override fun updatePassword(id: String, updatePasswordTO: UpdatePasswordTO) {
        val userFound: User = this.getUserActive(id)

        log.info("UserActive found: ${userFound.copy(password = "")} for id: $id")

        when {
            !passwordEncoder.matches(
                updatePasswordTO.oldPassword,
                userFound.password
            ) -> throw InvalidOldPasswordException()
            updatePasswordTO.oldPassword == updatePasswordTO.newPassword -> throw EqualPasswordException()
        }

        userRepository.save(userFound.copy(
            password = passwordEncoder.encode(updatePasswordTO.newPassword),
            updatedAt = OffsetDateTime.now()
        ))
    }

    override fun update(updateUserTO: UpdateUserTO): UserTO {
        log.info("UpdateUserTO: $updateUserTO")

        val userFound: User = this.getUserActive(updateUserTO.id)

        log.info("UserActive found: ${userFound.copy(password = "")} for id: ${userFound.id}")

        val newUpdateUserTO = UpdateUserTO(
            id = updateUserTO.id,
            username = if (updateUserTO.username.isNotEmpty()) updateUserTO.username else userFound.username,
            email = if (updateUserTO.email.isNotEmpty()) updateUserTO.email else userFound.email,
            note = if (updateUserTO.note.isNotEmpty()) updateUserTO.note else userFound.note
        )

        log.info("NewUpdateUserTO: $newUpdateUserTO")

        return userRepository.save(userFound.copy(
            username = newUpdateUserTO.username,
            email = newUpdateUserTO.email,
            note = newUpdateUserTO.note,
            updatedAt = OffsetDateTime.now()
        )).toTO()
    }

    override fun delete(id: String) {
        val userFound: User = getUserActive(id)

        log.info("User deleted: ${userFound.copy(password = "")} for id: $id")

        userRepository.save(
            userFound.copy(status = StatusEnum.INACTIVE, deletedAt = OffsetDateTime.now())
        )
    }

    private fun getUserActive(id: String): User {
        return userRepository.findByIdAndStatus(id) ?: throw UserNotFoundException()
    }
}
