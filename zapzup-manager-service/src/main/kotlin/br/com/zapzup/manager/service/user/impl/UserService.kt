package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.domain.to.user.CreateUserTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import br.com.zapzup.manager.service.user.mapper.toTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : IUserService {

    override fun create(createUserTO: CreateUserTO): UserTO {
        val encryptedPassword = passwordEncoder.encode(createUserTO.password)
        val user = createUserTO.toEntity().copy(password = encryptedPassword)
//        TODO("TRATAR EXCEPTIONS")

        return userRepository.save(user).toTO()
    }
}