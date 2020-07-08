package com.solabe.paydaybank.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solabe.paydaybank.App
import com.solabe.paydaybank.repository.AccountRepository
import com.solabe.paydaybank.repository.CustomerRepository
import com.solabe.paydaybank.repository.TransactionRepository
import com.solabe.paydaybank.ui.screens.auth.AuthVM
import com.solabe.paydaybank.ui.screens.dashboard.DashboardVM
import com.solabe.paydaybank.ui.screens.transactions_history.TransactionVM

class ViewModelFactory constructor(private val app: App,
                                   private val customerRepository: CustomerRepository,
                                   private val accountRepository: AccountRepository,
                                   private val transactionRepository: TransactionRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainVM::class.java) -> MainVM(app, customerRepository) as T
            modelClass.isAssignableFrom(AuthVM::class.java) -> AuthVM(customerRepository) as T
            modelClass.isAssignableFrom(DashboardVM::class.java) -> DashboardVM(accountRepository, transactionRepository) as T
            modelClass.isAssignableFrom(TransactionVM::class.java) -> TransactionVM(transactionRepository) as T
            else -> throw IllegalArgumentException()
        }
    }
}