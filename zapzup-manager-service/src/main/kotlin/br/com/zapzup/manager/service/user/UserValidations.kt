package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.domain.to.user.GetUsersFilter

object UserValidations {

    fun assertAndExtractParams(filter: GetUsersFilter): GetUsersFilter {
        if (filter.page <= 0 || filter.limit <= 0) {
            //TODO: Lançar exceção de limite de página invalida
        }

        return filter
    }
}