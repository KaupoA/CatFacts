package com.example.catfacts

import android.app.Application
import android.content.Context
import com.example.catfacts.di.DependencyInjection
import com.example.catfacts.di.DependencyInjectionImplementation

open class App : Application() {

    open val di: DependencyInjection by lazy {
        DependencyInjectionImplementation(getString(R.string.api_url))
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di