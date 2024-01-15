package com.mercadolivro.controller.request

import com.mercadolivro.enuns.CustomerStatus

data class PutCustomerRequest(
    var name: String,
    var email:String
)