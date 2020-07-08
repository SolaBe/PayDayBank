package com.solabe.paydaybank.ui.screens.main

import com.solabe.paydaybank.ui.screens.auth.AuthLsn
import com.solabe.paydaybank.ui.screens.dashboard.DashboardLsn
import com.solabe.paydaybank.ui.screens.transactions_history.TransactionHistoryLsn

interface FragmentLsn : AuthLsn, DashboardLsn, TransactionHistoryLsn {

    override fun onLoginSuccess(customerId: Long) {
        openDashboard(customerId)
    }

    override fun onRegisterSuccess(customerId: Long) {
        openDashboard(customerId)
    }

    override fun openTransacrionHistory_Dashboard(accountIds: List<Long>?) {
        openTransactionsHistory(accountIds)
    }

    override fun logoutDashboard() = logoutCustomer()


    override fun logoutTransactionsHistory() = logoutCustomer()

    fun openDashboard(customerId: Long)

    fun openTransactionsHistory(accountIds: List<Long>?)

    fun logoutCustomer()

}