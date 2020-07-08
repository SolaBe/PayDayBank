package com.solabe.paydaybank.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.solabe.paydaybank.data.models.remote.*
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PayBankApi {

    @POST("/authenticate")
    fun authenticate(@Body loginRequest: LoginRequest) : Deferred<Response<CustomerApi>>

    @GET("/accounts")
    fun getAccounts(@Query("customer_id") customerId: Long) : Deferred<Response<List<AccountApi>>>

    @POST("/customers")
    fun registerCustomer(@Body registerRequest: RegisterRequest) : Deferred<Response<CustomerApi>>

    @GET("/transactions")
    fun getMonthlyTransactions(@Query("account_id") accountsIds: List<Long>,
                               @Query(value = "date_gte", encoded = true) periodStart: String,
                               @Query(value = "date_lte", encoded = true) periodEnd: String,
                               @Query("_sort") sortBy: String = "date",
                               @Query("_order") order: String = "desc") : Deferred<Response<List<TransactionApi>>>

    @GET("/transactions")
    fun getTransactionsList(@Query("account_id") accountsIds: List<Long>,
                            @Query("_page") page: Int,
                            @Query("_limit") limit: Int,
                            @Query("_sort") sortBy: String = "date",
                            @Query("_order") order: String = "desc") : Deferred<Response<List<TransactionApi>>>

    object Factory {
        fun makeApiRepository(okHttpClient: OkHttpClient,
                              gson: Gson = GsonBuilder()
                                  .setPrettyPrinting()
                                  .create(),
                              endPoint: String): PayBankApi {
            return Retrofit.Builder()
                .baseUrl(endPoint)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(PayBankApi::class.java)
        }
    }
}