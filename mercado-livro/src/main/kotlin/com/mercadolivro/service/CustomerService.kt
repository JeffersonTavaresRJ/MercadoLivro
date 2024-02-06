package com.mercadolivro.service

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.enuns.Profile
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/*
 anotation @Service sobre a classe serve para fazer injeção de dependência
*/
@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {
    fun getAll(pageable: Pageable): Page<CustomerModel> {
        return customerRepository.findAll(pageable);
    }

    fun getById(id:Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow{NotFoundException(Errors.ML0002.message.format(id), Errors.ML0002.code)};
    }

    fun create(customer: CustomerModel){
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        customerRepository.save(customerCopy);
    }

    fun put(customer: CustomerModel){
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception();
        }
        customerRepository.save(customer);
    }

    fun delete(id:Int){
        if(!customerRepository.existsById(id)){
            throw Exception();
        }

        val customer = getById(id);
        val countBooks = bookService.deleteByCustomer(customer);

        if(countBooks >0){
            customer.status = CustomerStatus.DELETADO;
            put(customer);
        }else{
            customerRepository.delete(customer);
        }

    }

    fun inactivate(id: Int) {
        if(!customerRepository.existsById(id)){
            throw Exception();
        }

        val customer = getById(id);

        if(customer.status != CustomerStatus.ATIVO){
            throw Exception();
        }
        //bookService.cancelByCustomer(customer);

        customer.status = CustomerStatus.INATIVO;
        put(customer);

        bookService.cancelByCustomer(customer);

    }

    fun findByNameContaining(name: String): List<CustomerModel> {
        return customerRepository.findByNameContaining(name).toList()
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email);
    }
}