package com.solabe.paydaybank.data.api.api_services

import com.solabe.paydaybank.data.api.PayBankApi
import com.solabe.paydaybank.data.mappers.TransactionMapper
import com.solabe.paydaybank.data.models.remote.TransactionApi
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import javax.inject.Inject

class TransactionApiService @Inject constructor(private val api: PayBankApi) {

    suspend fun getMonthlyTransactions(accountsIds: List<Long>,
                                       periodStart: String,
                                       periodEnd: String) : RequestResult<List<TransactionApi>> {
        val response = api.getMonthlyTransactions(accountsIds, periodStart, periodEnd).await()
        if (response.isSuccessful && response.body() != null) {
            return RequestSuccess(response.body()!!)
        }
        return RequestError(status = response.code(), tw = null)
    }

    suspend fun getTransactionsList(accountsIds: List<Long>,
                                    itemsCount: Int) : RequestResult<Pair<List<TransactionApi>, Boolean>> {
        val page = itemsCount / TRANSACTIONS_PAGE_LIMIT + 1
        val response = api.getTransactionsList(accountsIds, page, TRANSACTIONS_PAGE_LIMIT).await()
        if (response.isSuccessful && response.body() != null) {
            val links = response.headers()["Links"]
            var lastPage = 0
            links?.let {
                val lastPageChar = it.substringAfterLast("_page=")[0]
                if (lastPageChar.isDigit()) {
                    lastPage = lastPageChar.toInt()
                }
            }
            return RequestSuccess(Pair(response.body()!!, page == lastPage))
        }
        return RequestError(status = response.code(), tw = null)
    }

    companion object {
        private const val TRANSACTIONS_PAGE_LIMIT = 10
    }
}