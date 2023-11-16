package com.mercadolivro.service

import com.mercadolivro.model.CustomerModel
import org.springframework.stereotype.Service

/*
a anotation @Service sobre a classe serve para fazer injeção de dependência
*/
@Service
class CustomerService {

    val customers = mutableListOf<CustomerModel>();

    fun getAll(name: String?):List<CustomerModel>{
        //se a variável não for nula, executa o bloco de código entre as chaves..
        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }
        return customers;
    }

    fun getById(id:String): CustomerModel{
        return customers.filter{ it.id==id.toInt() }.first();
    }

    fun create(customer: CustomerModel){

        customer.id= if(customers.isEmpty()){
            1
        }else{
            customers.last().id?.plus(1)
        }
        customers.add(element = customer);
    }

    fun put(customer: CustomerModel){
        //"let": bloco de código a ser executado após dentro das chaves..
        customers.filter {it.id==customer.id}.first().let {
            it.name = customer.name
            it.email= customer.email
        }
    }

    fun delete(id:String){
        customers.removeIf { it.id==id.toInt() };
    }
}