package com.solabe.paydaybank.ui.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.solabe.paydaybank.repository.CustomerRepository

class MainVM(application: Application,
    private val customerRepository: CustomerRepository) : AndroidViewModel(application) {

    fun getCustomerId() = customerRepository.getCustomerId()

    fun logout() = customerRepository.deleteCustomer()
}