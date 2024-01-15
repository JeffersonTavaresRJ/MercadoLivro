package com.mercadolivro.service

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

/*
 anotation @Service sobre a classe serve para fazer injeção de dependência
*/
@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService
) {
    fun getAll(name: String?):List<CustomerModel>{
        name?.let {
            return customerRepository.findByNameContaining(it);
        }
        return customerRepository.findAll().toList();
    }

    fun getById(id:Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow();
    }

    fun create(customer: CustomerModel){
        customerRepository.save(customer);
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
}