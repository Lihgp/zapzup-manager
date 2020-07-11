package br.com.zapzup.manager.service.file

import br.com.zapzup.manager.domain.to.file.FileTO
import org.springframework.web.multipart.MultipartFile

interface IFileService {

    fun saveFile(multipartFile: MultipartFile): FileTO
}