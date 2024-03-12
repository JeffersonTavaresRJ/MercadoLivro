package com.mercadolivro.helper

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Role
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun buildCustomer(id: Int?=null,
                  name: String="customer name",
                  email: String="${UUID.randomUUID()}@email.com",
                  status: CustomerStatus?= CustomerStatus.ATIVO,
                  password: String="password"): CustomerModel {
    return CustomerModel(
        id = id, name = name,
        email = email,
        password = password,
        status = status,
        roles = setOf(Role.CUSTOMER)
    )
}

fun buildPurchase(id: Int?=null,
                  books: MutableList<BookModel> = mutableListOf(),
                  customer: CustomerModel = buildCustomer(),
                  price: BigDecimal = BigDecimal.TEN,
                  nfe: String? = UUID.randomUUID().toString(),
                  createdAt: LocalDateTime = LocalDateTime.now()): PurchaseModel{
        return PurchaseModel(
            id = id,
            books = books,
            customer = customer,
            price = price,
            nfe = nfe,
            createdAt = createdAt)
}

