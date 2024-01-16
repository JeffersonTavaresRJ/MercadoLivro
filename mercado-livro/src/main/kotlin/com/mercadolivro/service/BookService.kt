package com.mercadolivro.service

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/*
 anotation @Service sobre a classe serve para fazer injeção de dependência
*/
@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun findAll(pageable: org.springframework.data.domain.Pageable):Page<BookModel>{
        return bookRepository.findAll(pageable);
    }

    fun findByNameContaining(name: String):List<BookModel>{
          return bookRepository.findByNameContaining(name);
    }
    fun findActives(pageable: Pageable):Page<BookModel>{
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable);
    }

    fun findById(id:Int): BookModel{
        return bookRepository.findById(id).orElseThrow();
    }

    fun create(book: BookModel){
        bookRepository.save(book);
    }

    fun put(book: BookModel){
        if(!bookRepository.existsById(book.id!!)){
            throw Exception();
        }
        bookRepository.save(book);
    }

    fun delete(id:Int){
        if(!bookRepository.existsById(id)){
            throw Exception();
        }
        val book = findById(id);
        book.status = BookStatus.DELETADO;
        bookRepository.save(book);
    }

    fun deleteByCustomer(customer: CustomerModel):Int {
        return statusByCustomer(customer, BookStatus.DELETADO);
    }

    fun cancelByCustomer(customer: CustomerModel):Int {
        return statusByCustomer(customer, BookStatus.CANCELADO);
    }

    private fun statusByCustomer(customer: CustomerModel, status: BookStatus): Int{
        val books = bookRepository.findByCustomer(customer);
        for(book in books){
            book.status = status
        }
        return bookRepository.saveAll(books).count();
    }
}