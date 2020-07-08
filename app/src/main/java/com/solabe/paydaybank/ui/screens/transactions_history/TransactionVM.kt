package com.solabe.paydaybank.ui.screens.transactions_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solabe.paydaybank.repository.TransactionRepository
import com.solabe.paydaybank.ui.models.Transaction
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TransactionVM(private val transactionRepository: TransactionRepository) : ViewModel() {

    val job = Job()
    val bgScope = CoroutineScope(Dispatchers.Default + Job(job))

    var accountsIds: List<Long>? = null
    val transactionHistoryLD by lazy { MutableLiveData<RequestResult<Pair<List<Transaction>, Boolean>>>() }

    fun getTransactionsList(itemsCount: Int = 0) {
        bgScope.launch {
            runCatching { transactionRepository.getTransactionsList(accountsIds, itemsCount) }
                .onSuccess { transactionHistoryLD.postValue(it) }
                .onFailure { transactionHistoryLD.postValue(RequestError(tw = it)) }
        }
    }
}