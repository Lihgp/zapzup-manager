package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.commons.exceptions.UserAlreadyExistsException
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.UserValidations
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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

    override fun getUsers(filter: GetUsersFilter): List<UserTO> {
        val validatedFilter = UserValidations.assertAndExtractParams(filter)

        val pageagle: Pageable = PageRequest.of(validatedFilter.page, validatedFilter.limit)

//        val offset = (validatedFilter.page - 1) * validatedFilter.limit

        val records = userRepository.findByQueryParams(
            validatedFilter.email,
            validatedFilter.name,
            validatedFilter.username,
            pageagle
        )

        return if (records.content.isNotEmpty()) {
            //TODO: Fazer o retorno caso tenha encotrado algum resultado. OBS: É necessário fazer a montagem
            // das páginas
            emptyList()
        } else {
            emptyList()
        }
    }
}