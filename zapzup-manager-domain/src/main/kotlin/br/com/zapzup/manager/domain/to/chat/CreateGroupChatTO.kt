package br.com.zapzup.manager.domain.to.chat

data class CreateGroupChatTO(
    val name: String,
    val description: String,
    val creatorUserId: String,
    val members: List<UserIdTO>
) {
    data class UserIdTO(
        val id: String
    )
}