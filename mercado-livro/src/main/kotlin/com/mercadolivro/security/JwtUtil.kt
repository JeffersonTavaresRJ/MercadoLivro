package com.mercadolivro.security


import com.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtil{

    //pega os valores do arquivo application.xml
    @Value("\${jwt.expiration}")
    private val expiration: Int?=null;
    //pega os valores do arquivo application.xml
    @Value("\${jwt.secret}")
    private val secret: String?=null;

    //pega os valores do arquivo application.xml
    @Value("\${jwt.algorithm}")
    private val algorithm: String?=null;

    fun generateToken(id: Int?): String?{

         return Jwts.builder()
            .expiration(Date(System.currentTimeMillis()+expiration!!))
            .subject(id.toString())
            .signWith(SecretKeySpec(secret!!.toByteArray(), algorithm))
            .compact();
    }

    fun isValidToken(token: String):Boolean {
        val claims = getClaims(token);

        if(claims.subject==null || claims.expiration==null || Date().after(claims.expiration)){
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        try{
            return Jwts.parser()
                .verifyWith(SecretKeySpec(secret!!.toByteArray(), algorithm))
                .build()
                .parseSignedClaims(token)
                .payload;
        }catch(ex:Exception){
            throw AuthenticationException("Token inválido", "9999");
        }
    }

    fun getSubject(token: String):String {
        return getClaims(token).subject
    }

}