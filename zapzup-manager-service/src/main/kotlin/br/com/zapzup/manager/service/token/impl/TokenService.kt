package br.com.zapzup.manager.service.token.impl

import br.com.zapzup.manager.commons.exceptions.InvalidTokenException
import br.com.zapzup.manager.domain.entity.Token
import br.com.zapzup.manager.domain.enums.TokenTypeEnum
import br.com.zapzup.manager.domain.to.token.GenerateTokenTO
import br.com.zapzup.manager.domain.to.token.TokenTO
import br.com.zapzup.manager.repository.TokenRepository
import br.com.zapzup.manager.service.email.IEmailService
import br.com.zapzup.manager.service.token.ITokenService
import br.com.zapzup.manager.service.token.mapper.toTO
import br.com.zapzup.manager.service.token.mapper.toTokenTOList
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class TokenService(
    private val tokenRepository: TokenRepository,
    private val userService: IUserService,
    private val emailService: IEmailService
) : ITokenService {

    private val log = LogManager.getLogger(this.javaClass)

    override fun generateToken(generateTokenTO: GenerateTokenTO, tokenType: String) {
        log.info("GenerateTokenTO: $generateTokenTO and tokenType: $tokenType")

        val userTO = userService.getByEmail(generateTokenTO.email)
        val token = tokenRepository.save(Token(
            user = userTO.toEntity(),
            expirationDate = OffsetDateTime.now().plusHours(1))
        )

        log.info("Token created: $token")

        emailService.sendEmail(generateTokenTO.email, token.code, TokenTypeEnum.valueOf(tokenType))
    }

    override fun validateToken(code: String) {
        log.info("Token code: $code")

        val token = tokenRepository.findByCode(code) ?: throw InvalidTokenException()
        val tokenTO = token.toTO()

        if (tokenTO.expirationDate.isBefore(OffsetDateTime.now())) {
            throw InvalidTokenException()
        }

        log.info("Token validated: $token")

        this.delete(tokenTO.id)
    }

    override fun getAll(): List<TokenTO> {
        val tokens = tokenRepository.findAll()

        return toTokenTOList(tokens)
    }

    override fun delete(id: String) {
        log.info("TokenId for delete: $id")

        tokenRepository.deleteById(id)
    }
}