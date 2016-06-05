package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class RecyclerViewHeaderExampleActivityTest {
    @Rule
    public ActivityTestRule<RecyclerViewHeaderExampleActivity> mActivityTestRule = new ActivityTestRule<>(RecyclerViewHeaderExampleActivity.class, false, false);

    @Test
    public void clickAll() {
        RecyclerViewHeaderExampleActivity activity = mActivityTestRule.launchActivity(null);
        MergeRecyclerAdapter mergeRecyclerAdapter = activity.mMergeRecyclerAdapter;

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        {
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mHeaderAdapter);
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.view_header, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(containsString("Header")), anyInt());
        }
        {
            int position = 3;
            String item = activity.mItemsAdapter.getItemAt(position);
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mItemsAdapter) + position;
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(item), anyInt());
        }
        {
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mFooterAdapter);
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.view_footer, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(containsString("Footer")), anyInt());
        }
    }
}
