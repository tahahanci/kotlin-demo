package com.hancidev.kotlindemo.service

import com.hancidev.kotlindemo.dto.CustomerDto
import com.hancidev.kotlindemo.entity.Customer
import com.hancidev.kotlindemo.repository.CustomerRepository
import com.hancidev.kotlindemo.service.mapper.CustomerMapper
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Service
class CustomerService(private val customerRepository: CustomerRepository,
    private val customerMapper: CustomerMapper) {

    fun getCustomerByEmail(email: String): Customer? {
        return customerRepository.findCustomerByEmail(email)
    }

    fun saveCustomer(customerDto: CustomerDto): Customer? {
        val birthDate = LocalDate.parse(customerDto.birthDate, DATE_FORMATTER)
        val age = Period.between(birthDate, LocalDate.now()).years

        if (age < 18) {
            throw IllegalArgumentException("Customer must be at least 18!")
        }

        if (isCustomerExist(customerDto.email)) {
            throw IllegalArgumentException("Customer exists with given email!")
        }

        val customer = customerMapper.customerFromCustomerDto(customerDto)
        return customerRepository.save(customer)
    }

    companion object {
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    fun deleteCustomer(email: String) {
        customerRepository.findCustomerByEmail(email)
            ?.let(customerRepository::delete)
    }

    fun getAllCustomers() : List<Customer> {
        return customerRepository.findAllByEmailIsNotNull()
    }

    private fun isCustomerExist(email: String) : Boolean {
        return customerRepository.findCustomerByEmail(email) != null
    }

}