package com.solabe.paydaybank.ui.screens.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solabe.paydaybank.App
import com.solabe.paydaybank.R
import com.solabe.paydaybank.di.component.ActivityComponent
import com.solabe.paydaybank.di.component.DaggerActivityComponent
import com.solabe.paydaybank.di.module.ActivityModule
import com.solabe.paydaybank.ui.screens.auth.AuthVM
import com.solabe.paydaybank.ui.screens.main.MainActivity
import com.solabe.paydaybank.ui.screens.main.ViewModelFactory
import com.solabe.paydaybank.utils.DateUtils
import com.solabe.paydaybank.utils.startEndPeriodFromMonthYear
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.time.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var categoriesAdapter: CategoriesAdapter? = null

    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(activity as MainActivity))
            .appComponent(App.get(requireContext()).appComponent)
            .build()
    }
    var viewModel: DashboardVM? = null
    private var dashboardLsn: DashboardLsn? = null

    override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DashboardVM::class.java)
        dashboardLsn = activity as DashboardLsn
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(dashboard_toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)
        observe()
        initCategoriesRv()

        view_all_transactions_b.setOnClickListener {
            dashboardLsn?.openTransacrionHistory_Dashboard(viewModel?.accounts?.ids)
        }

        viewModel?.getAccounts(getCustomerId())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            dashboardLsn?.logoutDashboard()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDateSpinner() {
        var bottomEdge: LocalDate? = null
        bottomEdge = if (viewModel?.accounts?.ealiestCreationDate == null ||
                         viewModel?.accounts?.ealiestCreationDate == 0L) {
            LocalDate.of(LocalDate.now().year, LocalDate.now().monthValue - 3, 1)
        } else {
            LocalDate.from(Instant.ofEpochSecond(viewModel!!.accounts!!.ealiestCreationDate!!)
                .atZone(ZoneId.systemDefault()))
        }
        val topEdge = LocalDate.now(Clock.systemUTC())
        DateUtils.getMonthYearList(topEdge, bottomEdge) { list ->
            dates_spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, list)
            dates_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val monthYear = list[position]
                    val (periodStart, periodEnd) = monthYear.startEndPeriodFromMonthYear()
                    viewModel?.getExpenses(periodStart, periodEnd)
                }
            }
        }
    }

    private fun initCategoriesRv() {
        categoriesAdapter = CategoriesAdapter(mutableListOf())
        categories_rv.adapter = categoriesAdapter
        categories_rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observe() {
        viewModel?.accountsLD?.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                viewModel?.accounts = it
                initDateSpinner()
                viewModel?.getExpenses(null, null)
            }.onFailure {
                Toast.makeText(requireContext(), getString(R.string.nothing_to_show_message), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel?.expenseCategoriesLD?.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                if (it.isEmpty()) {
                    categories_rv.isVisible = false
                    no_expense_tv.isVisible = true
                } else {
                    categories_rv.isVisible = true
                    no_expense_tv.isVisible = false
                    categoriesAdapter?.setItems(it)
                }
            }.onFailure {
                Toast.makeText(requireContext(),
                    getString(R.string.expense_categories_error_text),
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getCustomerId() = arguments?.getLong(CUSTOMER_ID) ?: 0L

    companion object {

        private const val CUSTOMER_ID = "customer_id"
        fun newInstance() = DashboardFragment()
        fun createArgs(customerId: Long) = Bundle().apply {
            putLong(CUSTOMER_ID, customerId)
        }
    }
}