package com.mercadolivro.service

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.enuns.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest{
    @MockK
    private lateinit var customerRepository: CustomerRepository;
    @MockK
    private lateinit var bookService: BookService;
    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder;

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun `create customer`(){

        val initialPassword = Math.random().toString();
        val fakeCustomer = buildCustomer(password = initialPassword);

        val fakePasswordEncrypted = UUID.randomUUID().toString();
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePasswordEncrypted);


        every{customerRepository.save(fakeCustomerEncrypted)} returns fakeCustomer;
        every{bCrypt.encode(initialPassword)} returns fakePasswordEncrypted;

        customerService.create(fakeCustomer);

        verify(exactly=1){customerRepository.save(fakeCustomerEncrypted)};
        verify(exactly=1){bCrypt.encode(initialPassword)};


    }

    @Test
    fun `byId Customer`(){
        val id = Random().nextInt();
        val fakeCustomer = buildCustomer(id=id);

        every{customerRepository.findById(id)}returns Optional.of(fakeCustomer);

        val customer = customerService.getById(id);

        assertEquals(fakeCustomer, customer);
        verify(exactly = 1){customerRepository.findById(id)};
    }

    @Test
    fun `byId Customer error not found`(){
        val id = Random().nextInt();

        every{customerRepository.findById(id)}returns Optional.empty();

        val error = assertThrows<NotFoundException>{customerService.getById(id)};

        assertEquals(Errors.ML0002.code, error.errorCode);
        assertEquals("Cliente [${id}] não existe", error.message);
        verify(exactly = 1){customerRepository.findById(id)};
    }

    fun buildCustomer(id: Int?=null,
                      name: String="customer name",
                      email: String="${UUID.randomUUID()}@email.com",
                      password: String="password"): CustomerModel{
        return CustomerModel(
            id=id, name= name,
            email=email,
            password=password,
            status = CustomerStatus.ATIVO,
            roles = setOf(Role.CUSTOMER)
        )

    }
}


