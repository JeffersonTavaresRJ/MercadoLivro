package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustomerController(
    val customerService: CustomerService
) {
    @GetMapping
    fun getAll(@PageableDefault(page=0, size=10) pageable: Pageable):Page<CustomerResponse>{
        return customerService.getAll(pageable).map { it.toResponse() };
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id:Int): CustomerResponse{
        return customerService.getById(id).toResponse();
    }

    @GetMapping("/name-contain/{name}")
    fun getCustomer(@PathVariable name:String): List<CustomerResponse>{
        return customerService.findByNameContaining(name).map{it.toResponse()};
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest){
        //aplicando extension function: toCustomerModel..
        customerService.create(customer.toCustomerModel());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun putCustomer(@PathVariable id:String, @RequestBody customer: PutCustomerRequest){
        val customerSaved = customerService.getById(id.toInt());
        //aplicando extension function: toCustomerModel..
        customerService.put(customer.toCustomerModel(customerSaved));
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun inactivateCustomer(@PathVariable id: String){
        customerService.inactivate(id.toInt());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id:Int){
        customerService.delete(id);
    }

}