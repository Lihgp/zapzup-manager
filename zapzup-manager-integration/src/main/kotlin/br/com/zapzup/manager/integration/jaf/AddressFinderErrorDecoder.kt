package br.com.zapzup.manager.integration.jaf

import br.com.zapzup.manager.commons.error.ZapZupErrorCode
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class AddressFinderErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception =
        when {
            response.status() == HttpStatus.BAD_REQUEST.value() ->
                throw RuntimeException(ZapZupErrorCode.GENERAL_ERROR.code)
            else ->
                throw RuntimeException(ZapZupErrorCode.GENERAL_ERROR.code)
        }
}