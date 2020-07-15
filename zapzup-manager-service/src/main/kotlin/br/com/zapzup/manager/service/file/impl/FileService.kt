package br.com.zapzup.manager.service.file.impl

import br.com.zapzup.manager.commons.utils.FileUtils
import br.com.zapzup.manager.domain.entity.File
import br.com.zapzup.manager.domain.to.file.FileTO
import br.com.zapzup.manager.repository.FileRepository
import br.com.zapzup.manager.service.file.IFileService
import br.com.zapzup.manager.service.file.mapper.toTO
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater


@Service
class FileService(
    private val fileRepository: FileRepository
) : IFileService {
    override fun saveFile(multipartFile: MultipartFile?): FileTO? {
        if (multipartFile == null) {
            return null
        }

        val compressedBytes = FileUtils.compressBytes(multipartFile.bytes)
        val file = File(
            name = multipartFile.originalFilename!!,
            type = multipartFile.contentType!!,
            fileByte = compressedBytes
        )

        return fileRepository.save(file).toTO()
    }

}