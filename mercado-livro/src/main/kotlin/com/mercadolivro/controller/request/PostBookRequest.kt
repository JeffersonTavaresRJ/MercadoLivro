package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PostBookRequest(
    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,
    @field:NotNull(message = "Preço deve ser informado")
    var price:BigDecimal,
    //Alias do parâmetro do request da API..
    @JsonAlias("customer_id")
    var customerId: Int
)