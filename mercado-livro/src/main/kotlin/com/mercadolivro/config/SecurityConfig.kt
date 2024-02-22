package com.mercadolivro.config

import com.mercadolivro.enuns.Role
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(private val customerRepository: CustomerRepository,
                     private val authenticationConfiguration: AuthenticationConfiguration,
                     private val userDetailsCustomService: UserDetailsCustomService,
                     private val jwtUtil: JwtUtil
) {
    private val PUBLIC_POST_MATCHERS = arrayOf("/customer");
    private val ADMIN_MATCHERS = arrayOf("/admin/**")

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.cors { it.disable() }.csrf{it.disable()}
        http.authorizeRequests{
            it.requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                .requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
            .anyRequest()
            .authenticated()
            .and()
            .authenticationProvider(authenticationProvider());
        }
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil));
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetailsCustomService, jwtUtil));
        http.sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)};


        val securityFilterChain = http.build();
        return securityFilterChain;
    }


    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager;
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder();
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsCustomService)
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder())
        return authenticationProvider
    }

}