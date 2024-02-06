package com.mercadolivro.model

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Profile
import jakarta.persistence.*

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?=null,

    @Column
    var name: String,

    @Column
    var email:String,

    @Column
    var password: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus?=null,


    @Column(name="Role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @CollectionTable(name="customer_roles", joinColumns = [JoinColumn(name="customer_id")])
    var roles: Set<Profile> = setOf()
)