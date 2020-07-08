package com.solabe.paydaybank.ui.screens.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.solabe.paydaybank.App
import com.solabe.paydaybank.R
import com.solabe.paydaybank.di.component.ActivityComponent
import com.solabe.paydaybank.di.component.DaggerActivityComponent
import com.solabe.paydaybank.di.module.ActivityModule
import com.solabe.paydaybank.ui.screens.auth.AuthFragment
import com.solabe.paydaybank.ui.screens.dashboard.DashboardFragment
import com.solabe.paydaybank.ui.screens.transactions_history.TransactionHistoryFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentLsn {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent(App.get(this).appComponent)
            .build()
    }
    private var viewModel: MainVM? = null
    private var currFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainVM::class.java)
        setContentView(R.layout.activity_main)
        if (viewModel?.getCustomerId() ?: 0L > 0L) {
            showMonthlyDashboardFragment(viewModel?.getCustomerId() ?: 0L)
        } else {
            showAuthFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMonthlyDashboardFragment(customerId: Long) {
        val fragment = DashboardFragment.newInstance()
        fragment.arguments = DashboardFragment.createArgs(customerId)
        replace(fragment = fragment, tag = "dashboard")
    }

    private fun showAuthFragment() = replace(AuthFragment.newInstance())

    private fun replace(fragment: Fragment, tag: String = "", addToBackStack: Boolean = false) {
        currFragment = fragment
        val transaction =  supportFragmentManager.beginTransaction()
        transaction.replace(frame.id, fragment)
        if (addToBackStack) transaction.addToBackStack(tag)
        transaction.commitAllowingStateLoss()
    }

    override fun openDashboard(customerId: Long) {
        showMonthlyDashboardFragment(customerId)
    }

    override fun openTransactionsHistory(accountIds: List<Long>?) {
        val fragment = TransactionHistoryFragment.newInstance()
        fragment.arguments = TransactionHistoryFragment.createArgs(accountIds)
        replace(fragment = fragment, tag = "transactionHistory", addToBackStack = true)
    }

    override fun logoutCustomer() {
        viewModel?.logout()
        while (supportFragmentManager.popBackStackImmediate()) {}
        showAuthFragment()
    }

}