package br.com.zapzup.manager.service.user.impl

import br.com.zapzup.manager.domain.to.user.GetUsersFilter
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.UserRepository
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.UserValidations.assertAndExtractParams

class UserService(
    private val userRepository: UserRepository
): IUserService {

    override fun getUsers(filter: GetUsersFilter): List<UserTO> {
        val validatedFilter = assertAndExtractParams(filter)

        val offset = (validatedFilter.page - 1) * validatedFilter.limit

        val records = userRepository.findByQueryParams(
            validatedFilter.email,
            validatedFilter.name,
            validatedFilter.username,
            offset,
            validatedFilter.limit
        )

        return if (records.isNotEmpty()) {
            //TODO: Fazer o retorno caso tenha encotrado algum resultado. OBS: É necessário fazer a montagem
            // das páginas
            emptyList()
        } else {
            emptyList()
        }
    }
}