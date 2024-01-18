package com.mercadolivro.controller.response
import com.mercadolivro.enuns.BookStatus
import java.math.BigDecimal

data class BookResponse (
    var id: Int?=null,
    var name: String,
    var price: BigDecimal,
    var customer: String?=null,
    var status: BookStatus?=null
)
