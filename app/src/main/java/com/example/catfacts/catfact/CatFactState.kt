package com.example.catfacts.catfact

import com.ww.roxie.BaseState

data class CatFactState(
    val loading: Boolean = false,
    val catFact: String = "",
    val displayError: Boolean = false
) : BaseState