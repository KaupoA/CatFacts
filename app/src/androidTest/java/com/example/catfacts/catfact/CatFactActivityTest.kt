package com.example.catfacts.catfact

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.catfacts.R
import com.example.catfacts.TestDependencyInjection
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class CatFactActivityTest {

    private val viewModel = TestDependencyInjection.catFactViewModel

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    @Test
    fun initialState() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.push_the_button)))
        assertEquals(viewModel.observableState.value, initialState)
    }

    @Test
    fun getFactButtonClickedAction() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.getFactButton)).perform(click())

        viewModel.testAction.assertValues(CatFactAction.GetFactButtonClicked)
    }

    @Test
    fun loadingState() {
        val initialState = CatFactState(loading = true)
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.loadingIndicator)).check(matches(isDisplayed()))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.push_the_button)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun factLoadedState() {
        val fact = "Even cats need to be tested"
        val initialState = CatFactState(catFact = fact)
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
    }
}