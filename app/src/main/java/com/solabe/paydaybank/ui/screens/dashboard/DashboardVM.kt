package com.solabe.paydaybank.ui.screens.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solabe.paydaybank.repository.AccountRepository
import com.solabe.paydaybank.repository.TransactionRepository
import com.solabe.paydaybank.ui.models.Accounts
import com.solabe.paydaybank.ui.models.ExpenseCategory
import com.solabe.paydaybank.utils.RequestError
import com.solabe.paydaybank.utils.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DashboardVM(private val accountRepository: AccountRepository,
                  private val transactionRepository: TransactionRepository) : ViewModel() {

    private val job = Job()
    private val bgScope = CoroutineScope(Dispatchers.Default + Job(job))

    val accountsLD by lazy { MutableLiveData<RequestResult<Accounts>>() }
    val expenseCategoriesLD by lazy { MutableLiveData<RequestResult<List<ExpenseCategory>>>() }
    var accounts: Accounts? = null

    fun getAccounts(customerId: Long) {
        bgScope.launch {
            runCatching { accountRepository.getAccounts(customerId) }
                .onSuccess { accountsLD.postValue(it) }
                .onFailure {
                    accountsLD.postValue(RequestError(tw = it))
                }
        }
    }

    fun getExpenses(periodStart: String?,
                    periodEnd: String?) {
        bgScope.launch {
            runCatching { transactionRepository.getMonthlyTransactions(accounts?.ids,
                                                                       periodStart,
                                                                       periodEnd)
            }
                .onSuccess { expenseCategoriesLD.postValue(it) }
                .onFailure {
                    expenseCategoriesLD.postValue(RequestError(tw = it))
                }
        }
    }
}