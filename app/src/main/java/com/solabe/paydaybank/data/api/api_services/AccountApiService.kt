package com.solabe.paydaybank.data.api.api_services

import com.solabe.paydaybank.data.api.PayBankApi
import com.solabe.paydaybank.data.models.remote.AccountApi
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import javax.inject.Inject

class AccountApiService @Inject constructor(private val api: PayBankApi) {

    suspend fun getAccounts(customerId: Long) : RequestResult<List<AccountApi>> {
        val response = api.getAccounts(customerId).await()
        if (response.isSuccessful && response.body() != null) {
            return RequestSuccess(response.body()!!)
        }
        return RequestError(tw = null)
    }

}