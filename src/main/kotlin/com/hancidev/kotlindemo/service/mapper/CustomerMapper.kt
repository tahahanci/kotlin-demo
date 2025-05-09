package com.hancidev.kotlindemo.service.mapper

import com.hancidev.kotlindemo.dto.CustomerDto
import com.hancidev.kotlindemo.entity.Customer
import org.springframework.stereotype.Component

@Component
class CustomerMapper {

    fun customerFromCustomerDto(from: CustomerDto) : Customer {
        val customer = Customer(name = from.name, email = from.email)
        return customer
    }
}