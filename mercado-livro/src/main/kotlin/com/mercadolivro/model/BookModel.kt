package com.mercadolivro.model

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.exception.BadRequestException
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

    //muitos books para um customer..
    @ManyToOne
    @JoinColumn(name="Customer_id")
    var customer: CustomerModel?=null
){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus?=null
        set(value){
            if(field== BookStatus.CANCELADO || field == BookStatus.DELETADO){
                throw BadRequestException(Errors.ML0003.message.format(field), Errors.ML0003.code);
            }
            field = value;
        }
    constructor(id: Int?=null,
                name: String,
                price: BigDecimal,
                status: BookStatus?=null,
                customer: CustomerModel?=null
                ): this(id, name, price, customer){
            this.status = status;
    }
}