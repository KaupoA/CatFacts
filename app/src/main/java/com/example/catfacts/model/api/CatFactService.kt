package com.example.catfacts.model.api

import com.example.catfacts.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface CatFactService {

    @GET("facts")
    fun getFacts(): Single<Response>
}