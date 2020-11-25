package br.com.zapzup.manager.application.controller.address

import br.com.vican.jarvis.address.finder.api.representation.AddressRepresentation
import br.com.zapzup.manager.api.ResponseWrapper
import br.com.zapzup.manager.api.address.AddressApi
import br.com.zapzup.manager.integration.jaf.AddressClient
import br.com.zapzup.manager.integration.jaf.response.AddressResponse
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class AddressController(
    private val addressClient: AddressClient,
    private val restTemplate: RestTemplate
) : AddressApi {

    private val log = LogManager.getLogger(AddressController::class.java)
    @Value("\${address.finder.url}")
    private lateinit var addressFinderUrl: String

    override fun getAddressByZipcode(
        @PathVariable("zipcode", required = true) zipCode: String
    ): ResponseWrapper<AddressRepresentation> {
        log.info("Log received: $zipCode")

//        restTemplate.getForEntity("$addressFinderUrl/address/$zipCode", AddressRepresentation::class.java).body

        return ResponseWrapper(addressClient.findAddressByZipCode(zipCode))
    }

}