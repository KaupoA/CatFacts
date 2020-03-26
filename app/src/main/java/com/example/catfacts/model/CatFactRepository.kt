package com.example.catfacts.model

import com.example.catfacts.model.api.CatFactService
import io.reactivex.Single
import kotlin.random.Random

class CatFactRepository(val catFactService: CatFactService) {

    @Throws(Exception::class)
    fun getFact(): Single<String> {
        return catFactService.getFacts()
            .map { response ->
                val randomInt = if (response.all.size > 1) {
                    Random.nextInt(0, response.all.size - 1)
                } else {
                    0
                }
                response.all[randomInt].text
            }
    }
}
