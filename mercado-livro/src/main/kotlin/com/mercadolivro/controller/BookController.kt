package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("book")
class BookController(
    val customerService: CustomerService,
    val bookService: BookService
) {
    @GetMapping
    fun getAll(@RequestParam name: String?):List<BookModel>{
        return bookService.findAll(name);
    }

    @GetMapping("/actives")
    fun getActives():List<BookModel>{
        return bookService.findActives();
    }

    @GetMapping("/{id}")
    fun getBook(@PathVariable id:Int): BookModel{
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: PostBookRequest){
        val customer = customerService.getById(book.customerId)
        //aplicando extension function: toBookModel..
        bookService.create(book.toBookModel(customer));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun putBook(@PathVariable id:Int, @RequestBody book: PutBookRequest){
        val bookSaved = bookService.findById(id)
        //aplicando extension function: toBookModel..
        bookService.put(book.toBookModel(bookSaved));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id:Int){
        bookService.delete(id);
    }

}