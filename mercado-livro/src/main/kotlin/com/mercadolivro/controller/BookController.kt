package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.PageResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("book")
class BookController(
    private val customerService: CustomerService,
    private val bookService: BookService
) {

    @GetMapping
    fun getAll(@PageableDefault(page=0, size=10) pageable: Pageable): PageResponse<BookResponse> {
        return bookService.findAll(pageable).map { it.toResponse() }.toPageResponse();
    }

    @GetMapping("/actives")
    fun getActives(@PageableDefault(size=10, page = 0) pageable: Pageable):PageResponse<BookResponse>{
        return bookService.findActives(pageable).map { it.toResponse() }.toPageResponse();
    }

    @GetMapping("/{id}")
    fun getBook(@PathVariable id:Int): BookResponse{
        return bookService.findById(id).toResponse();
    }
    @GetMapping("/name-contain/{name}")
    fun getContainName(@PathVariable name: String):List<BookResponse>{
        return bookService.findByNameContaining(name).map { it.toResponse() };
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid book: PostBookRequest){
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