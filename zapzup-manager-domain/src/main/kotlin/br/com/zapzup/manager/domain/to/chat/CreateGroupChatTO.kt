package br.com.zapzup.manager.domain.to.chat

data class CreateGroupChatTO(
    val chatName: String,
    val chatDescription: String,
    val creatorUserId: String,
    val members: List<UserIdTO>
) {
    data class UserIdTO(
        val id: String
    )
}