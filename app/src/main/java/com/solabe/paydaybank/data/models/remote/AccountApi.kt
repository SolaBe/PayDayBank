package com.solabe.paydaybank.data.models.remote

import com.google.gson.annotations.SerializedName

data class AccountApi(@SerializedName("id") val id: Long,
                      @SerializedName("customer_id") val customerId: Long,
                      @SerializedName("iban") val iban: String,
                      @SerializedName("type") val type: String,
                      @SerializedName("date_created") val dateCreated: String,
                      @SerializedName("active") val isActive: Boolean) {
}