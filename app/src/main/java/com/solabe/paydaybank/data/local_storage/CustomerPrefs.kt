package com.solabe.paydaybank.data.local_storage

import com.solabe.paydaybank.data.models.local.Customer
import javax.inject.Inject

class CustomerPrefs @Inject constructor(private val prefsStorage: PrefsStorage) {

    fun saveCustomer(customer: Customer) {
        prefsStorage.saveCustomer(customer)
    }

    fun deleteCustomer() = prefsStorage.deleteCustomer()

    fun getCustomerId() = prefsStorage.getCustomerId()

}