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
        if (isCustomerExist(customerDto.email)) {
            throw IllegalArgumentException("Customer exist with given mail!")
        }

        val customer = customerMapper.customerFromCustomerDto(customerDto)
        return customerRepository.save(customer)
    }

    private fun isCustomerExist(email: String): Boolean {
        return customerRepository.findCustomerByEmail(email) != null
    }

}