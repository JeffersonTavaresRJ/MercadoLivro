package com.mercadolivro.security

import com.auth0.jwt.Algorithm
import com.auth0.jwt.JWTSigner
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtil{

    //pega os valores do arquivo application.xml
    @Value("\${jwt.expiration}")
    private val expiration: Int?=null;
    //pega os valores do arquivo application.xml
    @Value("\${jwt.secret}")
    private val secret: String?=null;

    fun generateToken(id: Int?): String?{
        val claims = HashMap<String, Any>();
        claims.put("sub", id.toString());

        val options = JWTSigner.Options()
            .setAlgorithm(Algorithm.HS512)
            .setExpirySeconds(expiration);

        return JWTSigner(secret).sign(claims, options);
    }
}