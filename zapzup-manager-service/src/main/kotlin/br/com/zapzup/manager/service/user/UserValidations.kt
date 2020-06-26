package br.com.zapzup.manager.service.user

import br.com.zapzup.manager.commons.exceptions.ValidationException
import br.com.zapzup.manager.domain.to.user.GetUsersFilter

object UserValidations {

    fun assertAndExtractParams(filter: GetUsersFilter): GetUsersFilter {
        if (filter.page < 0 || filter.limit <= 0) throw ValidationException()
        return filter
    }
}