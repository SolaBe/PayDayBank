package com.solabe.paydaybank.utils

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    val posEndLocal = this.indexOf("@")
    val localPartOfEmail = this.substring(0,if(posEndLocal >= 0) posEndLocal else 0)
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
            && localPartOfEmail.length <= 64
            && this.indexOf(".@") == -1
}

fun String.isValidPassword(): Boolean {
    var password = this
    val lengthBefore = password.length
    password = removeWhitespaces(password)
    return lengthBefore == password.length && password.length >= 6 && password.length <= 16
}

private fun removeWhitespaces(s: String): String {
    return s.replace("[\\s+]".toRegex(), "")
}