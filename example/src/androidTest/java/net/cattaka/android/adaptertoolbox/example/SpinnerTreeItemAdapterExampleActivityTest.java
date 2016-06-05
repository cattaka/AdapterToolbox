package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.AdapterView;

import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.spinner.SpinnerMyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.spinner.SpinnerMyTreeItemAdapter.WrappedItem;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils.Entry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.hasSelectedItem;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInAdapterView;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class SpinnerTreeItemAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<SpinnerTreeItemAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(SpinnerTreeItemAdapterExampleActivity.class, false, false);

    SpinnerTreeItemAdapterExampleActivity mActivity;
    AdapterConverter<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder, WrappedItem> mAdapterConverter;
    SpinnerMyTreeItemAdapter mAdapter;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mAdapterConverter = (AdapterConverter<SpinnerMyTreeItemAdapter, SpinnerMyTreeItemAdapter.ViewHolder, WrappedItem>) mActivity.mSpinner.getAdapter();
        mAdapter = mAdapterConverter.getOrig();
        mActivity.mSnackbarLogic = spy(new SnackbarLogic());
    }

    @Test
    public void openCloseAll() {
        onView(withId(R.id.spinner)).perform(click());

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            TestUtils.Entry<SpinnerMyTreeItemAdapter.WrappedItem> entry = find(mAdapter.getItems(), SpinnerMyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onData(is(entry.object)).onChildView(withId(R.id.check_opened))
                    .perform(click());
        }
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            TestUtils.Entry<SpinnerMyTreeItemAdapter.WrappedItem> entry = find(mAdapter.getItems(), SpinnerMyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onData(is(entry.object)).onChildView(withId(R.id.check_opened))
                    .perform(click());
        }
    }


    @Test
    public void clickSome() {
        Entry<WrappedItem> entry = find(mAdapter.getItems(), WrappedItem.class, 4);
        onView(withId(R.id.spinner)).perform(click());
        onData(is(entry.object)).perform(click());

        onView(withId(R.id.spinner)).check(hasSelectedItem(is(entry.object)));
        verify(mActivity.mSnackbarLogic).make(any(View.class), contains(entry.object.getItem().getText()), anyInt());
    }
}
