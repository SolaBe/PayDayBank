package com.solabe.paydaybank.ui.screens.auth

interface AuthLsn {

    fun onLoginSuccess(customerId: Long)
    fun onRegisterSuccess(customerId: Long)
}