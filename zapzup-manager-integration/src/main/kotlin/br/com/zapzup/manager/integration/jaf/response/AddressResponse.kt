package br.com.zapzup.manager.integration.jaf.response

import br.com.vican.jarvis.address.finder.domain.enums.State

data class AddressResponse(
    val zipCode: String?,
    val street: String?,
    val complement: String?,
    val neighbourhood: String?,
    val city: String?,
    val state: State?
)