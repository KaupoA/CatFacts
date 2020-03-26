package com.example.catfacts.catfact

import com.example.catfacts.model.CatFactRepository
import io.reactivex.Single

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImplementations(val catFactRepository: CatFactRepository) : CatFactUseCase {

    override fun getFact(): Single<String> {
        return catFactRepository.getFact()
    }
}