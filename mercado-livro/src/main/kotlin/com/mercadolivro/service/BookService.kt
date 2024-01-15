package com.mercadolivro.service

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

/*
 anotation @Service sobre a classe serve para fazer injeção de dependência
*/
@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun findAll(name: String?):List<BookModel>{
        name?.let {
            return bookRepository.findByNameContaining(it);
        }
        return bookRepository.findAll().toList();
    }

    fun findActives():List<BookModel>{
        return bookRepository.findByStatus(BookStatus.ATIVO).toList();
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