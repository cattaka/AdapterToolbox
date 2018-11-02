package net.cattaka.android.adaptertoolbox.example;

import androidx.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.allOf;
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
public class MultiAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<MultiAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(MultiAdapterExampleActivity.class, false, false);

    @Test
    public void clickAll() {
        MultiAdapterExampleActivity activity = mActivityTestRule.launchActivity(null);
        MergeRecyclerAdapter mergeRecyclerAdapter = activity.mMergeRecyclerAdapter;

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        {
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mStringsHeaderAdapter);
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.view_header, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Strings"), containsString("Header"))), anyInt());
        }
        {
            int position = 3;
            String item = activity.mStringsAdapter.getItemAt(position);
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mStringsAdapter) + position;
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(item), anyInt());
        }
        {
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mNumbersHeaderAdapter);
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.view_header, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Numbers"), containsString("Header"))), anyInt());
        }
        {
            int position = 4;
            Number item = activity.mNumbersAdapter.getItemAt(position);
            int globalPosition = TestUtils.calcPositionOffset(mergeRecyclerAdapter, activity.mNumbersAdapter) + position;
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, globalPosition)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(String.valueOf(item)), anyInt());
        }
    }
}
