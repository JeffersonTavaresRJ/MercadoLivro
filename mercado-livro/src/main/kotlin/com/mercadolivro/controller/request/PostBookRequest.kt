package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.enuns.BookStatus
import java.math.BigDecimal

data class PostBookRequest(
    var name: String,
    var price:BigDecimal,
    //Alias do parâmetro do request da API..
    @JsonAlias("customer_id")
    var customerId: Int
)