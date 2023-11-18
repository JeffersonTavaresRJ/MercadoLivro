package com.mercadolivro.repository

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {
    fun findByNameContaining(name: String?): List<BookModel>
    fun findByStatus(status: BookStatus): List<BookModel>
}