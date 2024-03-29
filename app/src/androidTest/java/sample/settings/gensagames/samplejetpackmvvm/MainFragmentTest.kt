package sample.settings.gensagames.samplejetpackmvvm

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sample.settings.gensagames.samplejetpackmvvm.inject.BaseInstrumentedTest
import sample.settings.gensagames.samplejetpackmvvm.model.SampleContextHelper
import sample.settings.gensagames.samplejetpackmvvm.utils.atPosition
import sample.settings.gensagames.samplejetpackmvvm.utils.isFeaturedViewHolder
import sample.settings.gensagames.samplejetpackmvvm.utils.waitFor
import sample.settings.gensagames.samplejetpackmvvm.view.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainFragmentTest : BaseInstrumentedTest() {

    companion object {
        private val ITEMS_TIME_TO = TimeUnit
            .SECONDS.toMillis(1)
        private const val ITEMS_MIN_SIZE = 15
    }

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var sampleContextHelper: SampleContextHelper

    @Test
    fun testStartReady() {
        val appContext = InstrumentationRegistry
            .getInstrumentation().targetContext

        Assert.assertEquals(
            "sample.settings.gensagames.samplejetpackmvvm",
            appContext.packageName
        )

        Assert.assertEquals(
            activityScenarioRule
                .scenario.state, Lifecycle.State.RESUMED
        )
    }

    @Test
    fun testInjectedObjects() {
        val appContext = InstrumentationRegistry
            .getInstrumentation().targetContext

        sampleContextHelper.logSampleInfo(appContext)
    }

    @Test
    fun testActiveLoading() {
        onView(withId(R.id.progress)).check(
            matches(
                withEffectiveVisibility(Visibility.VISIBLE)
            )
        )

        onView(isRoot()).perform(waitFor(ITEMS_TIME_TO))
        onView(withId(R.id.progress)).check(
            matches(
                withEffectiveVisibility(Visibility.GONE)
            )
        )
    }

    @Test
    fun testMinimumItems() {
        onView(isRoot()).perform(waitFor(ITEMS_TIME_TO))
        onView(withId(R.id.recyclerView)).check { v, _ ->
            v as RecyclerView
            Assert.assertTrue(
                v.adapter!!
                    .itemCount > ITEMS_MIN_SIZE
            )
        }
    }

    @Test
    fun testFeaturedViewHolder() {
        onView(isRoot()).perform(waitFor(ITEMS_TIME_TO))
        onView(withId(R.id.recyclerView))
            .perform(scrollToHolder(isFeaturedViewHolder()))
    }

    @Test
    fun testRecyclerViewItems() {
        onView(isRoot()).perform(waitFor(ITEMS_TIME_TO))

        for (i in 1..ITEMS_MIN_SIZE) {
            onView(withId(R.id.recyclerView))
                .perform(
                    actionOnItemAtPosition
                    <RecyclerView.ViewHolder>(i, click())
                )

            onView(isRoot()).perform(ViewActions.pressBack());

            onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(i))
                .check(
                    matches(
                        atPosition(
                            i,
                            hasDescendant(not(withText("")))
                        )
                    )
                )
        }
    }
}