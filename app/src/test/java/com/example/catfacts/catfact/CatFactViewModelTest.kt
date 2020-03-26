package com.example.catfacts.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.catfacts.RxTestSchedulerRule
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class CatFactViewModelTest {

    @get:Rule
    val instantExecutableRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTestSchedulerRule = RxTestSchedulerRule()

    val catFactUseCase = mock<CatFactUseCase>()

    val loadingState = CatFactState(loading = true)

    val observer = mock<Observer<CatFactState>>()

    lateinit var testSubject: CatFactViewModel

    @Before
    fun setup() {
        testSubject = CatFactViewModel(catFactUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given fact succesfully loaded, when action GetFact is recieved, then State contains fact`() {
        // GIVEN
        val fact = "Programmers love cats"
        val succesfulState = CatFactState(loading = false, catFact = fact)

        whenever(catFactUseCase.getFact()).thenReturn(Single.just(fact))

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        // THEN
        inOrder(observer){
            verify(observer).onChanged(CatFactState()) // initial state
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(succesfulState)
        }

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given fact failed to load, when action GetFact recieved, then state constains error`() {
        // GIVEN
        val errorState = CatFactState(displayError = true)
        whenever(catFactUseCase.getFact()).thenReturn(Single.error(RuntimeException()))

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        // THEN
        inOrder(observer){
            verify(observer).onChanged(CatFactState()) // initial state
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }

        verifyNoMoreInteractions(observer)
    }
}