package com.solabe.paydaybank.ui.screens.transactions_history

import android.os.Bundle
import android.sax.EndElementListener
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solabe.paydaybank.App
import com.solabe.paydaybank.R
import com.solabe.paydaybank.di.component.ActivityComponent
import com.solabe.paydaybank.di.component.DaggerActivityComponent
import com.solabe.paydaybank.di.module.ActivityModule
import com.solabe.paydaybank.ui.screens.main.MainActivity
import com.solabe.paydaybank.ui.screens.main.ViewModelFactory
import com.solabe.paydaybank.utils.EndlessScrollLsn
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_transaction_history.*
import javax.inject.Inject

class TransactionHistoryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var transactionsAdapter: TransactionsAdapter? = null
    private var endlessScrollLsn: EndlessScrollLsn? = null
    private var transactionHistoryLsn: TransactionHistoryLsn? = null
    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(activity as MainActivity))
            .appComponent(App.get(requireContext()).appComponent)
            .build()
    }

    var viewModel: TransactionVM? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TransactionVM::class.java)
        transactionHistoryLsn = activity as MainActivity
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(transaction_history_toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        viewModel?.accountsIds = getAccountsIds()?.asList()
        observe()
        initTransactionHistoryRv()
        viewModel?.getTransactionsList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            transactionHistoryLsn?.logoutTransactionsHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initTransactionHistoryRv() {
        val layoutManager = LinearLayoutManager(requireContext())
        transactions_rv.layoutManager = layoutManager
        transactionsAdapter = TransactionsAdapter()
        transactions_rv.adapter = transactionsAdapter
        endlessScrollLsn = object : EndlessScrollLsn(layoutManager) {
            override fun onLoadMore() {
                if (endlessScrollLsn?.loading == false) {
                    viewModel?.getTransactionsList(transactionsAdapter?.itemCount ?: 0)
                    endlessScrollLsn?.loading = true
                }
            }
        }
        transactions_rv.addOnScrollListener(endlessScrollLsn!!)
    }

    private fun observe() {
        viewModel?.transactionHistoryLD?.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { pair ->
                transactionsAdapter?.addItems(pair.first)
                if (pair.second) {
                    transactions_rv.removeOnScrollListener(endlessScrollLsn!!)
                }
                endlessScrollLsn?.loading = false
            }.onFailure {
                endlessScrollLsn?.loading = false
                transactions_rv.removeOnScrollListener(endlessScrollLsn!!)
            }
        })
    }

    private fun getAccountsIds() = arguments?.getLongArray(ACCOUNTS_IDS)

    companion object {

        const val ACCOUNTS_IDS = "accounts_ids"

        fun newInstance() = TransactionHistoryFragment()
        fun createArgs(ids: List<Long>?) = Bundle().apply {
            ids?.let { putLongArray(ACCOUNTS_IDS, it.toLongArray()) }
        }
    }
}