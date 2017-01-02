package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;

import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;

/**
 * Created by cattaka on 17/01/02.
 */
public class DifferenceDividerExampleActivityTest {
    @Rule
    public ActivityTestRule<DifferenceDividerExampleActivity> mActivityTestRule = new ActivityTestRule<>(DifferenceDividerExampleActivity.class, false, false);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;
    DifferenceDividerExampleActivity mActivity;

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
}