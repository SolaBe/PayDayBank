package com.solabe.paydaybank.data.local_storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.solabe.paydaybank.data.models.local.Customer

open class PrefsStorage(private val context: Context,
                        private val gson: Gson,
                        private val sharedPrefs : SharedPreferences =
                context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)) {

    fun saveCustomer(customer: Customer) {
        val customerString = gson.toJson(customer, Customer::class.java)
        sharedPrefs.edit { putString(CUSTOMER, customerString) }
    }
    fun deleteCustomer() = sharedPrefs.edit { remove(CUSTOMER) }
    fun getCustomerId() : Long {
        val customerString = sharedPrefs.getString(CUSTOMER, "")
        if (customerString.isNullOrEmpty()) {
            return -1L
        } else {
            return gson.fromJson(customerString, Customer::class.java).id
        }
    }

    companion object {
        private const val PREFS_FILENAME = "paydaybank_prefs"
        private const val CUSTOMER = "customer"
    }
}