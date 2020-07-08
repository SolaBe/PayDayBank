package com.solabe.paydaybank.repository

import com.solabe.paydaybank.data.api.api_services.AccountApiService
import com.solabe.paydaybank.data.mappers.AccountsMapper
import com.solabe.paydaybank.ui.models.Accounts
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountApiService: AccountApiService) {

    suspend fun getAccounts(customerId: Long) : RequestResult<Accounts> {
        if (customerId > 0) {
            accountApiService.getAccounts(customerId).onSuccess {
                return RequestSuccess(AccountsMapper.map(it))
            }.onFailure {
                return RequestError(tw = null)
            }
        }
        return RequestError(tw = null)
    }

}