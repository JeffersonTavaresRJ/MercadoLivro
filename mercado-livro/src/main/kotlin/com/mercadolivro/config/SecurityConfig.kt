package com.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig() {
    private val PUBLIC_POST_MATCHERS = arrayOf("/customer");

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.cors { it.disable() }.csrf{it.disable()}
        http.authorizeRequests{
            it.requestMatchers(*PUBLIC_POST_MATCHERS).permitAll()
            .anyRequest().authenticated()
        }
        http.sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
        return http.build()
    }


    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder();
    }
}