package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;

import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;
import net.cattaka.android.adaptertoolbox.example.utils.FlashColorItemAnimator.FlashColor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;

/**
 * Created by cattaka on 17/01/02.
 */
public class StringWithPayloadExampleActivityTest {
    @Rule
    public ActivityTestRule<StringWithPayloadExampleActivity> mActivityTestRule = new ActivityTestRule<>(StringWithPayloadExampleActivity.class, false, false);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;
    StringWithPayloadExampleActivity mActivity;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mRecyclerViewAnimatingIdlingResource = new RecyclerViewAnimatingIdlingResource(mActivity.mRecyclerView);
    }

    @Test
    public void testRun() {
        onView(withId(R.id.recycler)).perform(swipeUp());
        waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 5000);
        onView(withId(R.id.recycler)).perform(swipeDown());
        waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 5000);
    }

    @Test
    public void checkPayloadValue() {
        onView(withIdInRecyclerView(R.id.button_red, R.id.recycler, 0)).perform(click());
        onView(withIdInRecyclerView(R.id.text_payload, R.id.recycler, 0)).check(matches(withText(String.valueOf(FlashColor.RED))));
        onView(withIdInRecyclerView(R.id.button_blue, R.id.recycler, 1)).perform(click());
        onView(withIdInRecyclerView(R.id.text_payload, R.id.recycler, 1)).check(matches(withText(String.valueOf(FlashColor.BLUE))));
    }
}