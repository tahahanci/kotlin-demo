package com.hancidev.kotlindemo.controller

import com.hancidev.kotlindemo.dto.CustomerDto
import com.hancidev.kotlindemo.entity.Customer
import com.hancidev.kotlindemo.service.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping("/{email}")
    fun getCustomer(@PathVariable email: String): Customer? {
        return customerService.getCustomerByEmail(email)
    }

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto): Customer? {
        return customerService.saveCustomer(customerDto)
    }

    @DeleteMapping("/{email}")
    fun deleteCustomer(@PathVariable email: String) {
        customerService.deleteCustomer(email)
    }

    @GetMapping
    fun getAllCustomers() : List<Customer> {
        return customerService.getAllCustomers()
    }
}