package com.mercadolivro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserCustomDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
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

    @Test
    fun `all customers get name`(){
        val customer1 = customerRepository.save(buildCustomer(name = "Gustavo"));
         mockMvc.perform(get("/customer/name-contain/Gus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    fun `get by id (somente os dados do usuário que faz a consulta)`(){
        val customer = customerRepository.save(buildCustomer());
        mockMvc.perform(get("/customer/${customer.id}").with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status!!.name));

    }

    @Test
    fun `get by id (dados do usuário diferente que faz a consulta)`(){
        val customer = customerRepository.save(buildCustomer());
        mockMvc.perform(get("/customer/16").with(user(UserCustomDetails(customer))))
            .andExpect(status().isForbidden) /*NÃO AUTORIZADO*/

    }

    @Test
    @WithMockUser(roles=["ADMIN"])/*MOCANDO A ROLE ADMIN..*/
    fun `get by id (role=ADMIN)`(){
        val customer = customerRepository.save(buildCustomer());
        mockMvc.perform(get("/customer/${customer.id}"))
            .andExpect(status().isOk)

    }

    @Test
    fun `post customer`(){
        val request = PostCustomerRequest("fakeName", "email@fake.com", "123");
        mockMvc.perform(post("/customer")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated);

        /*confere se existe somente 01 registro gravado..*/
        val customers = customerRepository.findAll().toList();
        assertEquals(1, customers.size);
        assertEquals(request.name, customers[0].name);
        assertEquals(request.email, customers[0].email);
    }

    @Test
    fun `post customer for errors informations`(){
        val request = PostCustomerRequest("", "email@fake.com", "123");
        mockMvc.perform(post("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Request Inválido"))
            .andExpect(jsonPath("$.internalCode").value("ML-0000"));
    }

    @Test
    fun `update customer`(){
        val customer = customerRepository.save(buildCustomer());
        val request = PutCustomerRequest("fakeName", "email@fake.com");

        mockMvc.perform(put("/customer/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent);

        /*confere se existe somente 01 registro gravado..*/
        val customers = customerRepository.findAll().toList();
        assertEquals(1, customers.size);
        assertEquals(request.name, customers[0].name);
        assertEquals(request.email, customers[0].email);
    }

    @Test
    fun `delete customer`(){
        val customer = customerRepository.save(buildCustomer());
        mockMvc.perform(delete("/customer/${customer.id}"))
            .andExpect(status().isNoContent);
        val customers = customerRepository.findAll().toList();
        assertEquals(0, customers.size);


    }

}