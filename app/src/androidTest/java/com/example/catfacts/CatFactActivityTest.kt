package com.example.catfacts

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.catfacts.catfact.CatFactActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class CatFactActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    val mockWebServer = MockWebServer()

    val resources: Resources
        get() = activityRule.activity.resources

    val fact =
        "The frequency of a domestic cat's purr is the same at which muscles and bones repair themselves."
    val json =
        "{\"all\":[{\"_id\":\"58e008ad0aac31001185ed0c\",\"text\":\"$fact\",\"type\":\"cat\",\"user\":{\"_id\":\"58e007480aac31001185ecef\",\"name\":{\"first\":\"Kasimir\",\"last\":\"Schulz\"}},\"upvotes\":13,\"userUpvoted\":null}]}"

    @Test
    fun initialState() {
        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
    }

    @Test
    fun getFactButtonPressed_factLoadedSuccess() {
        val mockResponse = MockResponse()
        mockResponse.setBody(json)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
    }

    @Test
    fun getFactButtonPressed_errorOccured() {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(400)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start(8500)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.catFactView)).check(matches(withText(resources.getString(R.string.push_the_button))))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }
}