package br.com.zapzup.manager.integration.jaf

import br.com.vican.jarvis.address.finder.api.representation.AddressRepresentation
import br.com.zapzup.manager.integration.jaf.response.AddressResponse
import feign.Headers
import feign.Param
import feign.RequestLine

@Headers("Accept: application/json")
interface AddressClient {

    @RequestLine(value = "GET /address/{zipcode}")
//    @Headers(value = ["Content-type: application/json"])
    fun findAddressByZipCode(@Param("zipcode") zipCode: String): AddressRepresentation
}