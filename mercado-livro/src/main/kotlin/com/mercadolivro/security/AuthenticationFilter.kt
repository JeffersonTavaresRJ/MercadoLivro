package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controller.request.LoginRequest
import com.mercadolivro.repository.CustomerRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import com.mercadolivro.exception.AuthenticationException
import jakarta.servlet.FilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        try {
            /*transforma o objeto vindo do request e cria um objeto LoginRequest..*/
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java);
            /*pegando o id para montar o token..*/
            val id = customerRepository.findByEmail(loginRequest.email)?.id;
            val authToken=UsernamePasswordAuthenticationToken(id, loginRequest.password);
            /*retornando a autenticação..*/
            return authenticationManager.authenticate(authToken);
        }catch (ex: Exception){
            throw AuthenticationException("Falha ao antenticar", "999");
        }


    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id;
        val token = jwtUtil.generateToken(id);
        response.addHeader("Authorization", "Bearer $token");
    }
}