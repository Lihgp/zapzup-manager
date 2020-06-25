package br.com.zapzup.manager.service.reset.impl

import br.com.zapzup.manager.commons.exceptions.InvalidTokenException
import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.enums.TokenTypeEnum
import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO
import br.com.zapzup.manager.domain.to.reset.TokenTO
import br.com.zapzup.manager.repository.TokenRepository
import br.com.zapzup.manager.service.email.IEmailService
import br.com.zapzup.manager.service.reset.ITokenService
import br.com.zapzup.manager.service.reset.mapper.toResetPasswordTokenTOList
import br.com.zapzup.manager.service.reset.mapper.toTO
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class TokenService(
    private val tokenRepository: TokenRepository,
    private val userService: IUserService,
    private val emailService: IEmailService
) : ITokenService {

    override fun generateToken(generateTokenTO: GenerateTokenTO, tokenType: String) {
        val userTO = userService.findByEmail(generateTokenTO.email)
        val token = tokenRepository.save(Token(
            user = userTO.toEntity(),
            expirationDate = OffsetDateTime.now().plusHours(6))
        )

        emailService.sendEmail(generateTokenTO.email, token.code, TokenTypeEnum.valueOf(tokenType))
    }

    override fun validateToken(code: String) {
        val token = tokenRepository.findByCode(code) ?: throw InvalidTokenException()
        val tokenTO = token.toTO()

        if (tokenTO.expirationDate.isBefore(OffsetDateTime.now())){
            throw InvalidTokenException()
        }

        this.delete(tokenTO.id)
    }

    override fun getAll(): List<TokenTO> {
        val resetPasswordTokens = tokenRepository.findAll()

        return toResetPasswordTokenTOList(resetPasswordTokens)
    }

    override fun delete(id: String) {
        tokenRepository.deleteById(id)
    }
}