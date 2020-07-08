package com.solabe.paydaybank.ui.models


data class Customer(val firstName: String,
                    val lastName: String,
                    val gender: String,
                    val email: String,
                    val password: String,
                    val dateOfBirth: String,
                    val phone: String)

enum class Gender {
    MALE, FEMALE
}