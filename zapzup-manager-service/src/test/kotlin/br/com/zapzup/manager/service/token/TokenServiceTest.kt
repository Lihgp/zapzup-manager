package br.com.zapzup.manager.service.token

import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.entity.User
import br.com.zapzup.manager.domain.enums.StatusEnum
import br.com.zapzup.manager.domain.enums.TokenTypeEnum
import br.com.zapzup.manager.domain.to.token.GenerateTokenTO
import br.com.zapzup.manager.domain.to.user.UserTO
import br.com.zapzup.manager.repository.TokenRepository
import br.com.zapzup.manager.service.email.IEmailService
import br.com.zapzup.manager.service.token.impl.TokenService
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.assertj.core.api.Assertions
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.time.OffsetDateTime

class TokenServiceTest {
    private val tokenRepository: TokenRepository = mock(TokenRepository::class.java)
    private val userService: IUserService = mock(IUserService::class.java)
    private val emailService: IEmailService = mock(IEmailService::class.java)
    private val tokenService: ITokenService = TokenService(tokenRepository, userService, emailService)

    @Test
    fun `should send email with success`() {
        val userTO = buildUserTO()
        val token = buildToken()
        val generateTokenTO = GenerateTokenTO(email = userTO.email)
        val argumentCaptor = ArgumentCaptor.forClass(Token::class.java)

        `when`(userService.findByEmail(userTO.email)).thenReturn(userTO)
        `when`(tokenRepository.save(any(Token::class.java))).thenAnswer { token }

        tokenService.generateToken(generateTokenTO, "PASSWORD")

        verify(emailService, times(1))
            .sendEmail(userTO.email, token.code, TokenTypeEnum.PASSWORD)
        verify(tokenRepository, times(1)).save(argumentCaptor.capture())

        Assertions.assertThat(argumentCaptor.value).isEqualTo(token.copy(
            id = argumentCaptor.value.id,
            code = argumentCaptor.value.code,
            user = userTO.toEntity(),
            expirationDate = argumentCaptor.value.expirationDate
        ))
    }

    private fun buildUserTO(): UserTO =
        UserTO(
            id = "USER-ID",
            name = "Fulano",
            username = "fulaninho",
            note = "Suave",
            status = "ACTIVE",
            email = "fulano@gmail.com"
        )

    private fun buildToken(): Token =
        Token(
            id = "TOKEN-ID",
            code = "CODE",
            user = buildUser(),
            expirationDate = OffsetDateTime.now().plusHours(6)
        )

    private fun buildUser(): User =
        User(
            id = "USER-ID",
            name = "Fulano",
            username = "fulaninho",
            note = "Suave",
            status = StatusEnum.ACTIVE,
            email = "fulano@gmail.com"
        )
}
