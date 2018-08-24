package net.cattaka.android.adaptertoolbox.example;

import androidx.test.rule.ActivityTestRule;

import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;

/**
 * Created by cattaka on 17/01/02.
 */
public class VerticalListDividerExampleActivityTest {
    @Rule
    public ActivityTestRule<VerticalListDividerExampleActivity> mActivityTestRule = new ActivityTestRule<>(VerticalListDividerExampleActivity.class, false, false);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;
    VerticalListDividerExampleActivity mActivity;

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