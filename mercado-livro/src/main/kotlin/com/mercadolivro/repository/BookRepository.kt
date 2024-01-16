package com.mercadolivro.repository

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<BookModel, Int> {
    fun findByNameContaining(name: String?): List<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>

    /*no JpaRepository, permite-se acrescentar o pageable com parâmetro nas funções nativas..*/
    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>
    //fun findByStatus(status: BookStatus): List<BookModel>

    //fun findAll(pageable: Pageable): Page<BookModel> --já vem na biblioteca JpaRepository
}