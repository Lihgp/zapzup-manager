package br.com.zapzup.manager.domain.to.user

import br.com.zapzup.manager.commons.exceptions.ValidationException

data class GetUsersFilter(
    val email: String,
    val username: String,
    val name: String,
    val page: Int,
    val limit: Int
){
    fun hasFilter(): Boolean {
        return !(email.isNullOrBlank() && username.isNullOrBlank() && name.isNullOrBlank())
    }

    fun validateFilter() {
        if (page < 0 || limit <= 0) throw ValidationException()
    }
}