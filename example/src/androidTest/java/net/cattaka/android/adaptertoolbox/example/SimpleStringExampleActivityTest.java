package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.test.MockSnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class SimpleStringExampleActivityTest {
    @Rule
    public ActivityTestRule<SimpleStringExampleActivity> mActivityTestRule = new ActivityTestRule<>(SimpleStringExampleActivity.class, false, false);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;
    SimpleStringExampleActivity mActivity;
    ScrambleAdapter<Object> mAdapter;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mAdapter = (ScrambleAdapter<Object>) mActivity.mRecyclerView.getAdapter();
        mActivity.mSnackbarLogic = spy(new MockSnackbarLogic());
        mRecyclerViewAnimatingIdlingResource = new RecyclerViewAnimatingIdlingResource(mActivity.mRecyclerView);
    }

    @Test
    public void scroll() {
        onView(withId(R.id.recycler)).perform(swipeUp());
        waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 5000);
        onView(withId(R.id.recycler)).perform(swipeDown());
        waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 5000);
    }

    @Test
    public void clickItem_stringItem() {
        for (int i = 0; i < 10; i++) {
            TestUtils.Entry<String> entry = find(mAdapter.getItems(), String.class, i);
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, entry.index)).perform(click());

            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(containsString(String.valueOf(entry.object))),
                    anyInt()
            );
        }
    }
}
