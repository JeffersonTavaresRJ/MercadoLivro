package com.mercadolivro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

/*todos os requests para testar na chamada das apis mocadas..*/
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

/*todos os results para testar na chamada das apis mocadas..*/
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")/*annotation para ir no arquivo de configuração application-test.xml e apontar para o banco de dados de teste*/
@WithMockUser
class CustomerControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc;
    @Autowired
    private lateinit var customerRepository: CustomerRepository;
    @Autowired
    private lateinit var objectMapper: ObjectMapper;
    @BeforeEach
    fun delete()=customerRepository.deleteAll();
    @AfterEach
    fun tearDown()=customerRepository.deleteAll();

    @Test
    fun `all customers`(){
        val customer1 = customerRepository.save(buildCustomer());
        val customer2 = customerRepository.save(buildCustomer());
        mockMvc.perform(get("/customer"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("itens[0].id").value(customer1.id))
            .andExpect(jsonPath("itens[0].name").value(customer1.name))
            .andExpect(jsonPath("itens[0].email").value(customer1.email))
            .andExpect(jsonPath("itens[0].status").value(customer1.status!!.name))
            .andExpect(jsonPath("itens[1].id").value(customer2.id))
            .andExpect(jsonPath("itens[1].name").value(customer2.name))
            .andExpect(jsonPath("itens[1].email").value(customer2.email))
            .andExpect(jsonPath("itens[1].status").value(customer2.status!!.name));

    }

}