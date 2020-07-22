package br.com.zapzup.manager.service.file

import br.com.zapzup.manager.domain.entity.File
import br.com.zapzup.manager.repository.FileRepository
import br.com.zapzup.manager.service.file.impl.FileService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.springframework.mock.web.MockMultipartFile


class FileServiceTest {

    private val fileRepository: FileRepository = mock(FileRepository::class.java)
    private val fileService: IFileService = FileService(fileRepository)

    @Test
    fun `should save file with success`() {
        val file = MockMultipartFile(
            "data", "filename.txt", "text/plain", "some xml".toByteArray()
        )

        `when`(fileRepository.save(any(File::class.java))).thenAnswer {
            val argument = it.getArgument<File>(0)

            File(
                id = "FILE-ID",
                name = file.originalFilename,
                type = file.contentType!!,
                fileByte = argument.fileByte,
                createdAt = argument.createdAt
            )
        }

        val response = fileService.saveFile(file)

        assertThat(response).isNotNull
        assertThat(response?.id).isEqualTo("FILE-ID")
        assertThat(response?.name).isEqualTo(file.originalFilename)
        assertThat(response?.type).isEqualTo(file.contentType)
    }

    @Test
    fun `should return null for null multipart`() {
        val response = fileService.saveFile(null)

        assertThat(response).isNull()
    }
}
