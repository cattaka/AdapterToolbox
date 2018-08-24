package net.cattaka.android.adaptertoolbox.example;

import android.content.res.Resources;
import androidx.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
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
public class ScrambleAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<ScrambleAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<ScrambleAdapterExampleActivity>(ScrambleAdapterExampleActivity.class, false, false);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;
    ScrambleAdapterExampleActivity mActivity;
    ScrambleAdapter<Object> mAdapter;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mAdapter = (ScrambleAdapter<Object>) mActivity.mRecyclerView.getAdapter();
        mActivity.mSnackbarLogic = spy(new SnackbarLogic());
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
    public void clickItem_numberItem() {
        TestUtils.Entry<Number> entry = find(mAdapter.getItems(), Number.class, 0);
        onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
        onView(withIdInRecyclerView(R.id.text, R.id.recycler, entry.index)).perform(click());

        verify(mActivity.mSnackbarLogic).make(
                any(View.class),
                argThat(allOf(containsString("Number"), containsString(String.valueOf(entry.object)))),
                anyInt()
        );
    }

    @Test
    public void clickItem_stringItem() {
        TestUtils.Entry<String> entry = find(mAdapter.getItems(), String.class, 0);
        onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
        onView(withIdInRecyclerView(R.id.text, R.id.recycler, entry.index)).perform(click());

        verify(mActivity.mSnackbarLogic).make(
                any(View.class),
                argThat(allOf(containsString("String"), containsString(String.valueOf(entry.object)))),
                anyInt()
        );
    }

    @Test
    public void clickItem_ordinalLabelItem() {
        {   // Click code of OrdinalLabel item
            TestUtils.Entry<OrdinalLabel> entry = find(mAdapter.getItems(), OrdinalLabel.class, 0);
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.text_code, R.id.recycler, entry.index)).perform(click());

            Resources res = mActivity.getResources();
            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(
                            containsString("code of "),
                            containsString(String.valueOf(entry.object.getCode())),
                            containsString(String.valueOf(entry.object.getLabel(res)))
                    )),
                    anyInt()
            );
        }
        {   // Click code of OrdinalLabel item
            TestUtils.Entry<OrdinalLabel> entry = find(mAdapter.getItems(), OrdinalLabel.class, 0);
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.text_label, R.id.recycler, entry.index)).perform(click());

            Resources res = mActivity.getResources();
            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(
                            containsString("code of "),
                            containsString(String.valueOf(entry.object.getCode())),
                            containsString(String.valueOf(entry.object.getLabel(res)))
                    )),
                    anyInt()
            );
        }
    }

}
