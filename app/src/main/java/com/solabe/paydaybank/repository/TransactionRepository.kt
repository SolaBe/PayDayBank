package com.solabe.paydaybank.repository

import com.solabe.paydaybank.data.api.api_services.TransactionApiService
import com.solabe.paydaybank.data.mappers.TransactionMapper
import com.solabe.paydaybank.ui.models.ExpenseCategory
import com.solabe.paydaybank.ui.models.Transaction
import com.solabe.paydaybank.utils.DateUtils
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import com.solabe.paydaybank.utils.RequestSuccess
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionApiService: TransactionApiService) {

    suspend fun getMonthlyTransactions(accountsIds: List<Long>?,
                                       periodStart: String?,
                                       periodEnd: String?) : RequestResult<List<ExpenseCategory>> {
        if (accountsIds == null) return RequestError(tw = null)
        var pStart = periodStart
        var pEnd = periodEnd
        if (pStart == null && pEnd == null) {
            pStart = LocalDateTime.now(Clock.systemUTC()).withHour(23).withMinute(59)
                .format(DateTimeFormatter.ofPattern(DateUtils.DATETIME_FORMAT))
            pEnd = LocalDate.now(Clock.systemUTC()).atStartOfDay().minusMonths(1L)
                .format(DateTimeFormatter.ofPattern(DateUtils.DATETIME_FORMAT))
        }
        transactionApiService.getMonthlyTransactions(accountsIds, pStart!!, pEnd!!).onSuccess {
            return RequestSuccess(TransactionMapper.map(it))
        }.onFailure {
            return RequestError(tw = null)
        }
        return RequestError(tw = null)
    }

    suspend fun getTransactionsList(accountsIds: List<Long>?, itemsCount: Int)
                                        : RequestResult<Pair<List<Transaction>, Boolean>> {
        if (accountsIds == null) return RequestError(tw = null)
        transactionApiService.getTransactionsList(accountsIds, itemsCount).onSuccess {
            (items, isLastPage) ->
                return RequestSuccess(Pair(items.map { TransactionMapper.map(it) }, isLastPage))
        }.onFailure {
            return RequestError(tw = null)
        }
        return RequestError(tw = null)
    }
}