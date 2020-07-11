package br.com.zapzup.manager.service.file.impl

import br.com.zapzup.manager.domain.entity.File
import br.com.zapzup.manager.domain.to.file.FileTO
import br.com.zapzup.manager.repository.FileRepository
import br.com.zapzup.manager.service.file.IFileService
import br.com.zapzup.manager.service.file.mapper.toTO
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater

@Service
class FileService(
    private val fileRepository: FileRepository
) : IFileService {
    override fun saveFile(multipartFile: MultipartFile): FileTO {
        val compressedBytes = compressBytes(multipartFile.bytes)
        val file = File(
            name = multipartFile.originalFilename!!,
            type = multipartFile.contentType!!,
            fileByte = compressedBytes
        )

        return fileRepository.save(file).toTO()
    }


    private fun compressBytes(data: ByteArray): ByteArray {
        val deflate = Deflater()
        deflate.setInput(data)
        deflate.finish()

        val outputStream = ByteArrayOutputStream(data.size)
        val buffer = ByteArray(size = 1024)

        while (!deflate.finished()) {
            val count = deflate.deflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()

        return outputStream.toByteArray()
    }

}