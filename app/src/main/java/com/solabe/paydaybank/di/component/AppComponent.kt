package com.solabe.paydaybank.di.component

import com.solabe.paydaybank.App
import com.solabe.paydaybank.di.PerApp
import com.solabe.paydaybank.di.module.AppModule
import com.solabe.paydaybank.di.module.NetModule
import com.solabe.paydaybank.di.module.PrefsModule
import com.solabe.paydaybank.di.module.ViewModelModule
import com.solabe.paydaybank.ui.screens.main.ViewModelFactory
import dagger.Component

@PerApp
@Component(modules = [
    AppModule::class,
    NetModule::class,
    PrefsModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun inject(app: App)

    fun viewModelFactory() : ViewModelFactory
}