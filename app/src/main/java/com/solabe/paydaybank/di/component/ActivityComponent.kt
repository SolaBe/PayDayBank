package com.solabe.paydaybank.di.component

import com.solabe.paydaybank.di.PerActivity
import com.solabe.paydaybank.di.module.ActivityModule
import com.solabe.paydaybank.ui.screens.auth.AuthFragment
import com.solabe.paydaybank.ui.screens.dashboard.DashboardFragment
import com.solabe.paydaybank.ui.screens.main.MainActivity
import com.solabe.paydaybank.ui.screens.transactions_history.TransactionHistoryFragment
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(baseActivity: MainActivity)
    fun inject(authFragment: AuthFragment)
    fun inject(dahsboardFragment: DashboardFragment)
    fun inject(transactionsFragment: TransactionHistoryFragment)
}