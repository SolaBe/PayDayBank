package com.solabe.paydaybank.ui.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.solabe.paydaybank.App
import com.solabe.paydaybank.R
import com.solabe.paydaybank.di.component.ActivityComponent
import com.solabe.paydaybank.di.component.DaggerActivityComponent
import com.solabe.paydaybank.di.module.ActivityModule
import com.solabe.paydaybank.ui.models.Customer
import com.solabe.paydaybank.ui.models.Gender
import com.solabe.paydaybank.ui.screens.main.MainActivity
import com.solabe.paydaybank.ui.screens.main.MainVM
import com.solabe.paydaybank.ui.screens.main.ViewModelFactory
import com.solabe.paydaybank.utils.isValidEmail
import com.solabe.paydaybank.utils.isValidPassword
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(activity as MainActivity))
            .appComponent(App.get(requireContext()).appComponent)
            .build()
    }
    var viewModel: AuthVM? = null
    var authLsn: AuthLsn? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthVM::class.java)
        authLsn = activity as AuthLsn
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initListeners()
    }

    private fun initListeners() {
        auth_tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                login_group.isVisible = tab?.position == 0
                register_group.isVisible = tab?.position == 1
            }
        })
        male_b.setOnClickListener { viewModel?.gender = Gender.MALE }
        female_b.setOnClickListener { viewModel?.gender = Gender.FEMALE }
        login_b.setOnClickListener {
            if (checkLoginFields()) {
                viewModel?.login(email_tiet.text.toString(), password_tiet.text.toString())
            }
        }
        register_b.setOnClickListener {
            if (checkRegistrationFields()) {
                viewModel?.register(Customer(firstName = first_name_tiet.text.toString(),
                    lastName = last_name_tiet.text.toString(),
                    phone = phone_tiet.text.toString(),
                    email = email_reg_tiet.text.toString(),
                    password = password_reg_tiet.text.toString(),
                    dateOfBirth = dob_tiet.text.toString(),
                    gender = viewModel?.gender?.name ?: Gender.MALE.name))
            }
        }
    }

    private fun checkLoginFields() : Boolean {
        if (email_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.email_required_message), Toast.LENGTH_LONG).show()
            return false
        } else {
            if (!email_tiet.text.toString().isValidEmail()) {
                Toast.makeText(requireContext(), getString(R.string.email_not_valid_message), Toast.LENGTH_LONG).show()
                return false
            }
        }
        if (password_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.password_required_message), Toast.LENGTH_LONG).show()
            return false
        } else {
            if (!password_tiet.text.toString().isValidPassword()) {
                Toast.makeText(requireContext(), getString(R.string.short_password_message), Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }

    private fun checkRegistrationFields() : Boolean {
        if (first_name_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.first_name_required_message), Toast.LENGTH_LONG).show()
            return false
        }
        if (last_name_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.last_name_required_message), Toast.LENGTH_LONG).show()
            return false
        }
        if (phone_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.phone_required_message), Toast.LENGTH_LONG).show()
            return false
        }
        if (email_reg_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.email_required_message), Toast.LENGTH_LONG).show()
            return false
        } else {
            if (!email_reg_tiet.text.toString().isValidEmail()) {
                Toast.makeText(requireContext(), getString(R.string.email_not_valid_message), Toast.LENGTH_LONG).show()
                return false
            }
        }
        if (password_reg_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.password_required_message), Toast.LENGTH_LONG).show()
            return false
        } else {
            if (!password_reg_tiet.text.toString().isValidPassword()) {
                Toast.makeText(requireContext(), getString(R.string.short_password_message), Toast.LENGTH_LONG).show()
                return false
            }
        }
        if (dob_tiet.text == null) {
            Toast.makeText(requireContext(), getString(R.string.dob_reguired_message), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun observe() {
        viewModel?.loginLD?.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                authLsn?.onLoginSuccess(it)
            }.onFailure {
                Toast.makeText(requireContext(), getString(R.string.login_failder_message), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel?.registerLD?.observe(viewLifecycleOwner, Observer {
                result ->
            result.onSuccess {
                authLsn?.onRegisterSuccess(it)
            }.onFailure {
                Toast.makeText(requireContext(), getString(R.string.registration_error_message), Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}