package net.cattaka.android.adaptertoolbox.example;

import androidx.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/08.
 */
public class FizzBuzzExampleActivityTest {
    @Rule
    public ActivityTestRule<FizzBuzzExampleActivity> mActivityTestRule = new ActivityTestRule<>(FizzBuzzExampleActivity.class, false, false);

    @Test
    public void click_FizzBuzz() {
        FizzBuzzExampleActivity activity = mActivityTestRule.launchActivity(null);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        {   // Click Integer
            int target = 2;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Fizz
            int target = 3;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Fizz "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Buzz
            int target = 5;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Buzz "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click FizzBuzz
            int target = 15;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("FizzBuzz "), containsString(String.valueOf(target)))), anyInt());
        }
    }

    @Test
    public void click_Fizz() {
        FizzBuzzExampleActivity activity = mActivityTestRule.launchActivity(null);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        // Disable buzz
        onView(withId(R.id.check_buzz)).perform(click());

        {   // Click Integer
            int target = 2;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Fizz
            int target = 3;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Fizz "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Integer
            int target = 5;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Fizz
            int target = 15;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Fizz "), containsString(String.valueOf(target)))), anyInt());
        }
    }

    @Test
    public void click_Buzz() {
        FizzBuzzExampleActivity activity = mActivityTestRule.launchActivity(null);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        // Disable fizz
        onView(withId(R.id.check_fizz)).perform(click());

        {   // Click Integer
            int target = 2;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Integer
            int target = 3;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Buzz
            int target = 5;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Buzz "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Buzz
            int target = 15;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Buzz "), containsString(String.valueOf(target)))), anyInt());
        }
    }

    @Test
    public void click_Integer() {
        FizzBuzzExampleActivity activity = mActivityTestRule.launchActivity(null);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        // Disable fizz buzz
        onView(withId(R.id.check_fizz)).perform(click());
        onView(withId(R.id.check_buzz)).perform(click());

        {   // Click Integer
            int target = 2;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Integer
            int target = 3;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Integer
            int target = 5;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
        {   // Click Integer
            int target = 15;
            int position = target - 1;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.text, R.id.recycler, position)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), argThat(allOf(containsString("Integer "), containsString(String.valueOf(target)))), anyInt());
        }
    }
}
