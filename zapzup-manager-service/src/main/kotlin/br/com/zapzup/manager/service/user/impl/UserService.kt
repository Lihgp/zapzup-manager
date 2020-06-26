package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.commons.exceptions.UserNotFoundException
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.UserValidations
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

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

    override fun getUsers(filter: GetUsersFilter): Page<UserTO> {
        val validatedFilter = UserValidations.assertAndExtractParams(filter)

        val pageagle: Pageable = PageRequest.of(validatedFilter.page, validatedFilter.limit, Sort.by("name"))

        return if (validatedFilter.hasFilter()) userRepository.findByQueryParams(
            validatedFilter.email,
            validatedFilter.name,
            validatedFilter.username,
            pageagle
        ).map { user -> user.toTO() }
        else userRepository.findAll(pageagle).map { user -> user.toTO() }
    }

    override fun getUserById(userId: String): UserTO {
        return userRepository.findById(userId).orElseThrow{ UserNotFoundException(id = userId) }.toTO()
    }
}