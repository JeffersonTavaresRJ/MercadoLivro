package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PostPurchaseRequest(
    @field:NotNull
    @field:Positive
    @JsonAlias("customer_id")
    val customerId: Int,
    @field:NotNull
    @JsonAlias("books_id")
    val bookIds: Set<Int>
)
