package com.solabe.paydaybank.di.module

import android.content.Context
import com.google.gson.Gson
import com.solabe.paydaybank.data.local_storage.PrefsStorage
import com.solabe.paydaybank.di.AppContext
import dagger.Module
import dagger.Provides
import com.solabe.paydaybank.di.PerApp

@Module
class PrefsModule {

    @Provides
    @PerApp
    fun providePrefsRepository(@AppContext context: Context, gson: Gson) = PrefsStorage(context, gson = gson)
}