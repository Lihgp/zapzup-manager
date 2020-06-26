package br.com.zapzup.manager.domain.to.user

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
}