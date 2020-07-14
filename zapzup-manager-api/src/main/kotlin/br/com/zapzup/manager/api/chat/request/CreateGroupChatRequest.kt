package br.com.zapzup.manager.api.chat.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

data class CreateGroupChatRequest (
    @field:[NotEmpty]
    @JsonProperty(value = "name")
    val name: String,
    @JsonProperty(value = "description")
    val description: String,
    @field:[NotEmpty]
    @JsonProperty(value = "creatorUserId")
    val creatorUserId: String,
    @JsonProperty(value = "members")
    val members: List<UserId>
) {
    data class UserId(
        @JsonProperty(value = "id")
        val id: String
    )
}