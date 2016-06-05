package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils.Entry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.hasSelectedItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class SpinnerScrambleAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<SpinnerScrambleAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(SpinnerScrambleAdapterExampleActivity.class, false, false);

    SpinnerScrambleAdapterExampleActivity mActivity;
    ClassicScrambleAdapter<Object> mAdapter;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mAdapter = (ClassicScrambleAdapter<Object>) mActivity.mSpinner.getAdapter();
        mActivity.mSnackbarLogic = spy(new SnackbarLogic());
    }

    @Test
    public void run() {
        Entry<String> entry = find(mAdapter.getOrig().getItems(), String.class, 4);
        onView(withId(R.id.spinner)).perform(click());
        onData(is(entry.object)).perform(click());

        onView(withId(R.id.spinner)).check(hasSelectedItem(is(entry.object)));
        verify(mActivity.mSnackbarLogic).make(any(View.class), contains(entry.object), anyInt());
    }
}
