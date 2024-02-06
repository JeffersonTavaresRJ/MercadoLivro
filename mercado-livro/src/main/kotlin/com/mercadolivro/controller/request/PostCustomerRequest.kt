package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(
    @field: NotEmpty(message = "Nome deve ser informado")
    var name: String,
    @field: EmailAvailable
    var email:String,
    @field:NotEmpty(message = "A senha deve ser informada")
    var password: String
)