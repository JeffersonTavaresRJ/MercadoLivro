package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(private val customerService: CustomerService, private val bookService: BookService) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel{
        val customer = customerService.getById(request.customerId);
        val books = bookService.getByIds(request.bookIds);

        books.forEach {
            if( it.status!=BookStatus.ATIVO){
            throw BadRequestException(Errors.ML0004.message.format(it.name, it.status), Errors.ML0004.code);
            }
        }

        return PurchaseModel(customer=customer,
                             books = books.toMutableList(),
                             price = books.sumOf { it.price });
    }
}