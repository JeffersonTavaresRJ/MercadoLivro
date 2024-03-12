package com.mercadolivro.repository

import com.mercadolivro.helper.buildCustomer
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/*como o repository é uma inteface, tem que sinalizar que os métodos das interfaces serão executadas*/
@SpringBootTest

/*annotation para ir no arquivo de configuração application-test.xml*/
@ActiveProfiles("test")

@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired /*não será mocado: será executado de fato*/
    private lateinit var customerRepository: CustomerRepository;

    @BeforeEach /*antes de executar o teste, ele deleta tudo*/
    fun delete()=customerRepository.deleteAll();

    @Test
    fun `customer findByNameContaining`(){
        val Bia = customerRepository.save(buildCustomer(name="Bia"));
        val Lia = customerRepository.save(buildCustomer(name="Lia"));
        val Beto = customerRepository.save(buildCustomer(name="Beto"));

        val customers = customerRepository.findByNameContaining("ia");

        assertEquals(listOf(Bia, Lia), customers);
    }

    @Nested /*para organizar os testes dentro de um mesmo contexto*/
    inner class `exists by email`{
        @Test
        fun `exists email`(){
            val email = "email@email.com";

            customerRepository.save(buildCustomer(email=email));

            val exists = customerRepository.existsByEmail(email);

            assertTrue(exists);
        }

        @Test
        fun `not exists email`(){
            val email = "emailnotexists@email.com";
            val exists = customerRepository.existsByEmail(email);
            assertFalse(exists);
        }
    }

    @Nested /*para organizar os testes dentro de um mesmo contexto*/
    inner class `find by email`{
        @Test
        fun `find email`(){
            val email = "findemail@email.com";

            val customerExpected = customerRepository.save(buildCustomer(email=email));

            val customerAtual = customerRepository.findByEmail(email);

            assertNotNull(customerAtual);
            assertEquals(customerExpected, customerAtual);
        }

        @Test
        fun `not find email`(){
            val email = "emailnotfind@email.com";
            val customer = customerRepository.findByEmail(email);
            assertNull(customer);
        }
    }

}