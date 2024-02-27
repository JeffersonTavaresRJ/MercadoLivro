package com.mercadolivro.controller.response

class PageResponse<T>(
    var itens: List<T>,
    var totalItens: Long,
    var currentPage:Int,
    var totalPages: Int
)