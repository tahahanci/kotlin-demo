package com.hancidev.kotlindemo.repository

import com.hancidev.kotlindemo.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findCustomerByEmail(name: String) : Customer?

    fun findAllByEmailIsNotNull() : List<Customer>
}