package com.hancidev.kotlindemo.service.logger

import com.hancidev.kotlindemo.repository.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CustomerLogger(private val customerRepository: CustomerRepository) {

    private val logger = LoggerFactory.getLogger(CustomerLogger::class.java)

    @Scheduled(fixedRate = 15000)
    fun logCustomerCount() {
        val count = customerRepository.count()
        logger.info("Customer table has $count records")
    }
}