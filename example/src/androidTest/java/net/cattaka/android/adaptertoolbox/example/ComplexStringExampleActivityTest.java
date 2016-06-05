package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class ComplexStringExampleActivityTest {
    @Rule
    public ActivityTestRule<ComplexStringExampleActivity> mActivityTestRule = new ActivityTestRule<>(ComplexStringExampleActivity.class, false, false);

    @Test
    public void clickItems() {
        ComplexStringExampleActivity activity = mActivityTestRule.launchActivity(null);
        ScrambleAdapter<String> adapter = (ScrambleAdapter<String>) activity.mRecyclerView.getAdapter();

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        for (int i = 0; i < 10; i++) {
            TestUtils.Entry<String> item = TestUtils.find(adapter.getItems(), String.class, i);
            onView(withId(R.id.recycler)).perform(scrollToPosition(i));
            {
                onView(TestUtils.withIdInRecyclerView(R.id.text, R.id.recycler, i)).perform(click());
                verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString(item.object), containsString("(Text)"))), anyInt());
            }
            {
                onView(TestUtils.withIdInRecyclerView(R.id.button_a, R.id.recycler, i)).perform(click());
                verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString(item.object), containsString("(A)"))), anyInt());
            }
            {
                onView(TestUtils.withIdInRecyclerView(R.id.button_b, R.id.recycler, i)).perform(click());
                verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString(item.object), containsString("(B)"))), anyInt());
            }
        }
    }
}
