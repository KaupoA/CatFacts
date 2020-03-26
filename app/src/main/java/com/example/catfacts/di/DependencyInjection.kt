package com.example.catfacts.di

import com.example.catfacts.catfact.CatFactAction
import com.example.catfacts.catfact.CatFactState
import com.example.catfacts.catfact.CatFactUseCaseImplementations
import com.example.catfacts.catfact.CatFactViewModel
import com.example.catfacts.model.api.CatFactService
import com.example.catfacts.model.CatFactRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ww.roxie.BaseViewModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
    val catFactViewModel: BaseViewModel<CatFactAction, CatFactState>
}

class DependencyInjectionImplementation(apiUrl: String) : DependencyInjection {

    override lateinit var catFactViewModel: BaseViewModel<CatFactAction, CatFactState>

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(apiUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val catFactService = retrofit.create(CatFactService::class.java)

        val catFactRepository = CatFactRepository(catFactService)
        val catFactUseCase = CatFactUseCaseImplementations(catFactRepository)
        catFactViewModel = CatFactViewModel(catFactUseCase)
    }
}