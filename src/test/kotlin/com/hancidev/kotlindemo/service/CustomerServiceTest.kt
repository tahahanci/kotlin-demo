package com.hancidev.kotlindemo.service

import com.hancidev.kotlindemo.dto.CustomerDto
import com.hancidev.kotlindemo.entity.Customer
import com.hancidev.kotlindemo.repository.CustomerRepository
import com.hancidev.kotlindemo.service.mapper.CustomerMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`

class CustomerServiceTest {

    private val customerRepository: CustomerRepository = mock(CustomerRepository::class.java)
    private val customerMapper: CustomerMapper = mock(CustomerMapper::class.java)
    private val customerService = CustomerService(customerRepository, customerMapper)

    @Test
    fun `getCustomerByEmail should return customer when email exists`() {
        val email = "test@example.com"
        val customer = Customer(1, "Test", email)

        `when`(customerRepository.findCustomerByEmail(email)).thenReturn(customer)

        val result = customerService.getCustomerByEmail(email)

        assertThat(result).isNotNull
        assertThat(result).isEqualTo(customer)
        verify(customerRepository).findCustomerByEmail(email)
    }

    @Test
    fun `getCustomerByEmail should return null when email does not exist`() {
        val email = "nonexistent@example.com"

        `when`(customerRepository.findCustomerByEmail(email)).thenReturn(null)

        val result = customerService.getCustomerByEmail(email)

        assertThat(result).isNull()
        verify(customerRepository).findCustomerByEmail(email)
    }

    @Test
    fun `saveCustomer should save and return customer when email does not exist`() {
        val email = "new@example.com"
        val customerDto = CustomerDto("New", email)
        val customer = Customer(1, "New", email)

        `when`(customerRepository.findCustomerByEmail(email)).thenReturn(null)
        `when`(customerMapper.customerFromCustomerDto(customerDto)).thenReturn(customer)
        `when`(customerRepository.save(customer)).thenReturn(customer)

        val result = customerService.saveCustomer(customerDto)

        assertThat(result).isNotNull
        assertThat(result).isEqualTo(customer)
        verify(customerRepository).findCustomerByEmail(email)
        verify(customerMapper).customerFromCustomerDto(customerDto)
        verify(customerRepository).save(customer)
    }

    @Test
    fun `saveCustomer should throw exception when email already exists`() {
        val email = "existing@example.com"
        val customerDto = CustomerDto("Existing", email)
        val existingCustomer = Customer(1, "Existing", email)

        `when`(customerRepository.findCustomerByEmail(email)).thenReturn(existingCustomer)

        val exception = assertThrows<IllegalArgumentException> {
            customerService.saveCustomer(customerDto)
        }

        assertThat(exception).hasMessage("Customer exist with given mail!")
        verify(customerRepository).findCustomerByEmail(email)
        verifyNoInteractions(customerMapper)
        verify(customerRepository, never()).save(any())
    }
}