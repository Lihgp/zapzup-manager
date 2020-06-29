package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.to.user.UpdateUserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : IUserService {

    override fun create(createUserTO: CreateUserTO): UserTO {
        when {
            userRepository.existsByUsername(createUserTO.username) -> throw UserAlreadyExistsException("username")
            userRepository.existsByEmail(createUserTO.email) -> throw UserAlreadyExistsException("email")
        }

        val encryptedPassword = passwordEncoder.encode(createUserTO.password)
        val user = createUserTO.toEntity().copy(password = encryptedPassword)

        return userRepository.save(user).toTO()
    }

    override fun update(updateUserTO: UpdateUserTO): UserTO {
        val userFound: User = userRepository.findById(updateUserTO.id).orElseThrow { UserNotFoundException() }

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
}