package net.cattaka.android.adaptertoolbox.example;

import androidx.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.adapter.ChoosableMyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
public class MultiChoosableTreeItemAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<MultiChoosableTreeItemAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(MultiChoosableTreeItemAdapterExampleActivity.class, false, true);

    RecyclerViewAnimatingIdlingResource mRecyclerViewAnimatingIdlingResource;

    @Before
    public void before() {
        mRecyclerViewAnimatingIdlingResource = new RecyclerViewAnimatingIdlingResource(mActivityTestRule.getActivity().mRecyclerView);
        // registerIdlingResources(mRecyclerViewAnimatingIdlingResource);
    }

    @After
    public void after() {
        // unregisterIdlingResources(mRecyclerViewAnimatingIdlingResource);
    }

    @Test
    public void openCloseAll() {
        MultiChoosableTreeItemAdapterExampleActivity activity = mActivityTestRule.getActivity();
        ChoosableMyTreeItemAdapter adapter = activity.mAdapter;

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        for (int i = 0; i < adapter.getItemCount(); i++) {
            TestUtils.Entry<ChoosableMyTreeItemAdapter.WrappedItem> entry = find(adapter.getItems(), ChoosableMyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.check_opened, R.id.recycler, entry.index)).perform(click());
            waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 3000);
        }
        for (int i = 0; i < adapter.getItemCount(); i++) {
            TestUtils.Entry<ChoosableMyTreeItemAdapter.WrappedItem> entry = find(adapter.getItems(), ChoosableMyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.check_opened, R.id.recycler, entry.index)).perform(click());
            waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 3000);
        }
    }

    @Test
    public void selectAndOk() {
        MultiChoosableTreeItemAdapterExampleActivity activity = mActivityTestRule.getActivity();
        ChoosableMyTreeItemAdapter adapter = activity.mAdapter;

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        MyTreeItem item3 = adapter.getItemAt(3).item;
        MyTreeItem item5 = adapter.getItemAt(5).item;
        {   // Select something
            int position = 3;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.check_chosen, R.id.recycler, position)).perform(click());
        }
        {   // Select something
            int position = 5;
            onView(withId(R.id.recycler)).perform(scrollToPosition(position));
            onView(withIdInRecyclerView(R.id.check_chosen, R.id.recycler, position)).perform(click());
        }
        {   // close all
            for (int i = 0; i < adapter.getItemCount(); i++) {
                TestUtils.Entry<ChoosableMyTreeItemAdapter.WrappedItem> entry = find(adapter.getItems(), ChoosableMyTreeItemAdapter.WrappedItem.class, i);
                if (entry.object.children.size() == 0) {
                    continue;
                }
                onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
                onView(withIdInRecyclerView(R.id.check_opened, R.id.recycler, entry.index)).perform(click());
                waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 3000);
            }
        }
        {   // Check OK button behavior.
            onView(withId(R.id.button_ok)).perform(click());
            verify(activity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(containsString(item3.getText()), containsString(item5.getText()))),
                    anyInt());
        }
    }
}
