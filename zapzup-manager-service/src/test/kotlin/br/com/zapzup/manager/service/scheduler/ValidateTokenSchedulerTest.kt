package br.com.zapzup.manager.service.scheduler

import br.com.zapzup.manager.domain.to.token.TokenTO
import br.com.zapzup.manager.service.token.ITokenService
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.time.OffsetDateTime

class ValidateTokenSchedulerTest {
    private val tokenService: ITokenService = mock(ITokenService::class.java)
    private val validateTokenScheduler: ValidateTokenScheduler = ValidateTokenScheduler(tokenService)

    @Test
    fun `should validate token with success`() {
        val tokenTOList = buildTokenTOList()

        `when`(tokenService.getAll()).thenReturn(tokenTOList)

        validateTokenScheduler.validateTokenExpirationDate()

        verify(tokenService, times(1)).delete(tokenTOList[0].id)
    }

    private fun buildTokenTOList(): List<TokenTO> {
        val token = TokenTO(
            id = "TOKEN-ID",
            code = "CODE",
            expirationDate = OffsetDateTime.now().minusHours(1)
        )

        return listOf(token)
    }
}