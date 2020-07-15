package br.com.zapzup.manager.domain.to.message

import org.springframework.web.multipart.MultipartFile

data class CreateMessageTO(
    val userId: String = "",
    val chatId: String = "",
    val content: String = "",
    val file: MultipartFile? = null
)