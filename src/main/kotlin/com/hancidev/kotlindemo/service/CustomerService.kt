package com.hancidev.kotlindemo.service

import com.hancidev.kotlindemo.dto.CustomerDto
import com.hancidev.kotlindemo.entity.Customer
import com.hancidev.kotlindemo.repository.CustomerRepository
import com.hancidev.kotlindemo.service.mapper.CustomerMapper
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository,
    private val customerMapper: CustomerMapper) {

    fun getCustomerByEmail(email: String): Customer? {
        return customerRepository.findCustomerByEmail(email)
    }

    fun saveCustomer(customerDto: CustomerDto): Customer? {
        customerRepository.findCustomerByEmail(customerDto.email)
            ?.let { throw IllegalArgumentException() }

        return customerMapper.customerFromCustomerDto(customerDto)
            .let(customerRepository::save)
    }

    fun deleteCustomer(email: String) {
        customerRepository.findCustomerByEmail(email)
            ?.let(customerRepository::delete)
    }

    fun getAllCustomers() : List<Customer> {
        return customerRepository.findAllByEmailIsNotNull()
    }

}