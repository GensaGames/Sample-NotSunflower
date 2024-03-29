package sample.settings.gensagames.samplejetpackmvvm.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import sample.settings.gensagames.samplejetpackmvvm.view.adapter.MainGridInfoAdapter

/**
 * Perform action of waiting for a specific time.
 */
fun waitFor(millis: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "Wait for $millis milliseconds."
        }

        override fun perform(uiController: UiController, view: View) {
            uiController.loopMainThreadForAtLeast(millis)
        }
    }
}

/**
 * Matches the [RecyclerView]s is featured
 */
fun isFeaturedViewHolder(): Matcher<MainGridInfoAdapter.InfoViewHolder> {
    return object : TypeSafeMatcher<MainGridInfoAdapter.InfoViewHolder>() {
        var isFirst : Boolean = false

        override fun matchesSafely(
            customHolder: MainGridInfoAdapter.InfoViewHolder): Boolean {
            if (customHolder.isFeatured() && !isFirst) {
                isFirst = true
                return true
            }
            return false
        }

        override fun describeTo(description: Description?) {
            description?.appendText(
                "item is featured. " +
                        "Our random doesn't work?"
            )
        }
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}