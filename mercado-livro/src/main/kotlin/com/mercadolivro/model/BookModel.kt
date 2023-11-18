package com.mercadolivro.model

import com.mercadolivro.enuns.BookStatus
import jakarta.annotation.security.DenyAll
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?=null,

    @Column
    var name: String,

    @Column
    var price:BigDecimal,

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus?=null,

    //muitos books para um customer..
    @ManyToOne
    @JoinColumn(name="Customer_id")
    var customer: CustomerModel?=null
)