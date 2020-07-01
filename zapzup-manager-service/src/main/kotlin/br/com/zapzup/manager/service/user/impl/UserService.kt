package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.commons.exceptions.EqualPasswordException
import br.com.zapzup.manager.commons.exceptions.InvalidOldPasswordException
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UpdatePasswordTO
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : IUserService {

    override fun create(createUserTO: CreateUserTO): UserTO {
        when {
            userRepository.existsByUsername(createUserTO.username) -> throw UserAlreadyExistsException("username")
            userRepository.existsByEmail(createUserTO.email) -> throw UserAlreadyExistsException("email")
        }

        val userInactive = userRepository.findByEmail(createUserTO.email)
        val encryptedPassword = passwordEncoder.encode(createUserTO.password)

        if (userInactive != null) {
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

    override fun findByEmail(email: String): UserTO {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()

        if (user.status == StatusEnum.INACTIVE) throw UserNotFoundException()

        return user.toTO()
    }

    override fun updatePassword(id: String, updatePasswordTO: UpdatePasswordTO) {
        val user = this.findUserActive(id)

        when {
            !passwordEncoder.matches(updatePasswordTO.oldPassword, user.password) -> throw InvalidOldPasswordException()
            updatePasswordTO.oldPassword == updatePasswordTO.newPassword -> throw EqualPasswordException()
        }

        userRepository.save(user.copy(
            password = passwordEncoder.encode(updatePasswordTO.newPassword),
            updatedAt = OffsetDateTime.now()
        ))
    }

    override fun update(updateUserTO: UpdateUserTO): UserTO {
        val userFound: User = this.findUserActive(updateUserTO.id)

        val newUpdateUserTO = UpdateUserTO(
            id = updateUserTO.id,
            username = if (updateUserTO.username.isNotEmpty()) updateUserTO.username else userFound.username,
            email = if (updateUserTO.email.isNotEmpty()) updateUserTO.email else userFound.email,
            note = if (updateUserTO.note.isNotEmpty()) updateUserTO.note else userFound.note
        )

        return userRepository.save(userFound.copy(
            username = newUpdateUserTO.username,
            email = newUpdateUserTO.email,
            note = newUpdateUserTO.note,
            updatedAt = OffsetDateTime.now()
        )).toTO()
    }

    override fun delete(id: String) {
        val userFound = findUserActive(id)

        userRepository.save(
            userFound.copy(status = StatusEnum.INACTIVE, deletedAt = OffsetDateTime.now())
        )
    }

    private fun findUserActive(id: String): User {
        return userRepository.findByIdAndStatus(id) ?: throw UserNotFoundException()
    }

}
