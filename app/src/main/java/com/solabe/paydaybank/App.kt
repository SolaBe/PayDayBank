package com.solabe.paydaybank

import android.app.Application
import android.content.Context
import com.solabe.paydaybank.di.component.AppComponent
import com.solabe.paydaybank.di.component.DaggerAppComponent
import com.solabe.paydaybank.di.module.AppModule
import com.solabe.paydaybank.di.module.NetModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent(this)
        appComponent.inject(this)
    }

    private fun buildAppComponent(app: App) : AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .netModule(NetModule(BuildConfig.baseUrl))
            .build()

    companion object {
        operator fun get(context: Context): App = context.applicationContext as App
    }
}