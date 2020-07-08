package com.solabe.paydaybank.data.models.remote

import com.google.gson.annotations.SerializedName

data class CustomerApi(@SerializedName("id") val id: Long,
                       @SerializedName("First Name") val firstName: String,
                       @SerializedName("Last Name") val lastName: String,
                       @SerializedName("gender") val gender: String,
                       @SerializedName("email") val email: String,
                       @SerializedName("password") val password: String,
                       @SerializedName("dob") val dateOfBirth: String,
                       @SerializedName("phone") val phone: String)

data class LoginRequest(@SerializedName("email") val email: String,
                        @SerializedName("password") val password: String)

data class RegisterRequest(@SerializedName("First Name") val firstName: String,
                           @SerializedName("Last Name") val lastName: String,
                           @SerializedName("gender") val gender: String,
                           @SerializedName("email") val email: String,
                           @SerializedName("password") val password: String,
                           @SerializedName("dob") val dateOfBirth: String,
                           @SerializedName("phone") val phone: String)