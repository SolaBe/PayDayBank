package com.solabe.paydaybank.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.solabe.paydaybank.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    fun provideActivity() : AppCompatActivity = activity

    @Provides
    @PerActivity
    fun provideActivityContext() = activity as Context

}