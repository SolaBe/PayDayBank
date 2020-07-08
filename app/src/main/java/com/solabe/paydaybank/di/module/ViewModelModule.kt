package com.solabe.paydaybank.di.module

import com.solabe.paydaybank.App
import com.solabe.paydaybank.di.PerApp
import com.solabe.paydaybank.repository.AccountRepository
import com.solabe.paydaybank.repository.CustomerRepository
import com.solabe.paydaybank.repository.TransactionRepository
import com.solabe.paydaybank.ui.screens.main.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    @PerApp
    fun provideViewModelFactory(app: App,
                                customerRepository: CustomerRepository,
                                accountRepository: AccountRepository,
                                transactionRepository: TransactionRepository) =
        ViewModelFactory(app,
            customerRepository,
            accountRepository,
            transactionRepository)
}