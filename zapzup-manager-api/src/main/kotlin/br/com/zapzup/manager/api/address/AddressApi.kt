package br.com.zapzup.manager.api.address

import br.com.vican.jarvis.address.finder.api.representation.AddressRepresentation
import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.integration.jaf.response.AddressResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Api(value = "Address", tags = ["Address"], description = "Address Resources")
@RequestMapping(value = ["/address"])
interface AddressApi {

    @GetMapping(value = ["/{zipcode}"])
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Gets a address by zipcode")
    fun getAddressByZipcode(
        @PathVariable(name = "zipcode", required = true) zipCode: String
    ): ResponseWrapper<AddressRepresentation>
}