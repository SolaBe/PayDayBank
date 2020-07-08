package com.solabe.paydaybank.data.models.local

data class Customer(val id: Long,
                    val firstName: String,
                    val lastName: String,
                    val gender: String,
                    val email: String,
                    val password: String,
                    val dateOfBirth: String,
                    val phone: String)