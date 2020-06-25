package br.com.zapzup.manager.service.reset.impl

import br.com.zapzup.manager.commons.exceptions.InvalidTokenException
import br.com.zapzup.manager.domain.entity.ResetPasswordToken
import br.com.zapzup.manager.domain.to.reset.GenerateTokenTO
import br.com.zapzup.manager.domain.to.reset.ResetPasswordTokenTO
import br.com.zapzup.manager.repository.ResetPasswordTokenRepository
import br.com.zapzup.manager.service.email.IEmailService
import br.com.zapzup.manager.service.reset.IResetPasswordService
import br.com.zapzup.manager.service.reset.mapper.toResetPasswordTokenTOList
import br.com.zapzup.manager.service.reset.mapper.toTO
import br.com.zapzup.manager.service.user.IUserService
import br.com.zapzup.manager.service.user.mapper.toEntity
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class ResetPasswordService(
    private val resetPasswordTokenRepository: ResetPasswordTokenRepository,
    private val userService: IUserService,
    private val emailService: IEmailService
) : IResetPasswordService {

    override fun generateResetToken(generateTokenTO: GenerateTokenTO) {
        val userTO = userService.findByEmail(generateTokenTO.email)
        val resetPasswordToken = resetPasswordTokenRepository.save(ResetPasswordToken(
            user = userTO.toEntity(),
            expirationDate = OffsetDateTime.now().plusHours(6))
        )

        emailService.sendEmail(generateTokenTO.email, resetPasswordToken.token)
    }

    override fun validateToken(token: String) {
        val resetPasswordToken = resetPasswordTokenRepository.findByToken(token) ?: throw InvalidTokenException()
        val resetPasswordTokenTO = resetPasswordToken.toTO()

        if (resetPasswordTokenTO.expirationDate.isBefore(OffsetDateTime.now())){
            throw InvalidTokenException()
        }

        this.delete(resetPasswordTokenTO.id)
    }

    override fun getAll(): List<ResetPasswordTokenTO> {
        val resetPasswordTokens = resetPasswordTokenRepository.findAll()

        return toResetPasswordTokenTOList(resetPasswordTokens)
    }

    override fun delete(id: String) {
        resetPasswordTokenRepository.deleteById(id)
    }
}