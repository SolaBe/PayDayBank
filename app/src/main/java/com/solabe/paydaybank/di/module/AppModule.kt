package com.solabe.paydaybank.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.solabe.paydaybank.App
import com.solabe.paydaybank.di.AppContext
import com.solabe.paydaybank.di.PerApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    @PerApp
    fun provideApp() : App = app

    @Provides
    @AppContext
    fun provideAppContext() : Context = app

    @Provides
    @PerApp
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.setLenient()
        return builder.create()
    }
}