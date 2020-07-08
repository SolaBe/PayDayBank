package com.solabe.paydaybank.repository

import com.solabe.paydaybank.data.api.api_services.CustomerApiService
import com.solabe.paydaybank.data.local_storage.CustomerPrefs
import com.solabe.paydaybank.data.mappers.CustomerMapper
import com.solabe.paydaybank.ui.models.Customer
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val customerPrefs: CustomerPrefs,
                                             private val customerApiService: CustomerApiService) {

    suspend fun login(email: String, password: String) : RequestResult<Long> {
        customerApiService.authenticate(email, password).onSuccess {
            customerPrefs.saveCustomer(CustomerMapper.map(it))
            return RequestSuccess(it.id)
        }.onFailure { RequestError(tw = null) }
        return RequestError(tw = null)
    }

    fun getCustomerId() = customerPrefs.getCustomerId()

    fun deleteCustomer() = customerPrefs.deleteCustomer()

    suspend fun register(customer: Customer) : RequestResult<Long> {
        customerApiService.register(CustomerMapper.map(customer)).onSuccess {
            customerPrefs.saveCustomer(CustomerMapper.map(it))
            return RequestSuccess(it.id)
        }.onFailure { RequestError(tw = null) }
        return RequestError(tw = null)
    }
}