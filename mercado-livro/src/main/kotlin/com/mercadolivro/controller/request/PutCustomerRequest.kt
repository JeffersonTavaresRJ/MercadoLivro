package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PutCustomerRequest(
    @field: NotEmpty(message = "Nome deve ser informado")
    var name: String,
    @field: Email(message = "E-mail inválido")
    var email:String
)