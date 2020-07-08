package com.solabe.paydaybank.di.module

import android.content.Context
import com.solabe.paydaybank.data.api.PayBankApi
import com.solabe.paydaybank.di.AppContext
import com.solabe.paydaybank.di.PerApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetModule(private val endpoint: String) {

    @Provides
    @PerApp
    fun provideApiRepository(okHttpClient: OkHttpClient) : PayBankApi =
            PayBankApi.Factory.makeApiRepository(okHttpClient = okHttpClient, endPoint = endpoint)

    @Provides
    @PerApp
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        return builder.addInterceptor(loggingInterceptor).build()
    }

}