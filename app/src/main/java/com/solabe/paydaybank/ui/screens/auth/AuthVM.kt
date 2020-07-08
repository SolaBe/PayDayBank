package com.solabe.paydaybank.ui.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solabe.paydaybank.repository.CustomerRepository
import com.solabe.paydaybank.ui.models.Customer
import com.solabe.paydaybank.ui.models.Gender
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AuthVM(private val customerRepository: CustomerRepository) : ViewModel() {

    val job = Job()
    val bgScope = CoroutineScope(Dispatchers.Default + Job(job))

    val loginLD by lazy { MutableLiveData<RequestResult<Long>>() }
    val registerLD by lazy { MutableLiveData<RequestResult<Long>>() }
    var gender: Gender = Gender.MALE

    fun login(email: String, password: String) {
        bgScope.launch {
            runCatching { customerRepository.login(email, password) }
                .onSuccess { loginLD.postValue(it) }
                .onFailure { loginLD.postValue(RequestError(tw = it)) }
        }
    }

    fun register(customer: Customer) {
        bgScope.launch {
            runCatching { customerRepository.register(customer) }
                .onSuccess { registerLD.postValue(it) }
                .onFailure { loginLD.postValue(RequestError(tw = it)) }
        }
    }
}