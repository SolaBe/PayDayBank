package com.solabe.paydaybank.data.models.remote

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionApi(@SerializedName("id") val id: Long,
                          @SerializedName("account_id") val accountId: Long,
                          @SerializedName("amount") val amount: BigDecimal,
                          @SerializedName("vendor") val vendor: String,
                          @SerializedName("category") val category: String,
                          @SerializedName("date") val date: String)