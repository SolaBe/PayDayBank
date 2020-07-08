package com.solabe.paydaybank.data.api.api_services

import com.solabe.paydaybank.data.api.PayBankApi
import com.solabe.paydaybank.data.models.remote.AccountApi
import com.solabe.paydaybank.data.models.remote.CustomerApi
import com.solabe.paydaybank.data.models.remote.LoginRequest
import com.solabe.paydaybank.data.models.remote.RegisterRequest
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import javax.inject.Inject

class CustomerApiService @Inject constructor(private val api: PayBankApi) {

    suspend fun authenticate(email: String, password: String) : RequestResult<CustomerApi> {
        val response = api.authenticate(LoginRequest(email, password)).await()
        if (response.isSuccessful && response.body() != null) {
            return RequestSuccess(response.body()!!)
        }
        return RequestError(status = response.code(), tw = null)
    }

    suspend fun register(registerRequest: RegisterRequest) : RequestResult<CustomerApi> {
        val response = api.registerCustomer(registerRequest).await()
        if (response.isSuccessful && response.body() != null) {
            return RequestSuccess(response.body()!!)
        }
        return RequestError(status = response.code(), tw = null)
    }
}