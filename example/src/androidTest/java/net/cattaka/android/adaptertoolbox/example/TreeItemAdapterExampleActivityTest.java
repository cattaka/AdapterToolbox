package net.cattaka.android.adaptertoolbox.example;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.adapter.MyTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;
import net.cattaka.android.adaptertoolbox.example.test.MockSnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.RecyclerViewAnimatingIdlingResource;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.waitForIdlingResource;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class TreeItemAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<TreeItemAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(TreeItemAdapterExampleActivity.class, false, true);

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
        TreeItemAdapterExampleActivity activity = mActivityTestRule.getActivity();
        MyTreeItemAdapter adapter = (MyTreeItemAdapter) activity.mRecyclerView.getAdapter();

        activity.mSnackbarLogic = spy(new MockSnackbarLogic());

        for (int i = 0; i < adapter.getItemCount(); i++) {
            TestUtils.Entry<MyTreeItemAdapter.WrappedItem> entry = find(adapter.getItems(), MyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.check_opened, R.id.recycler, entry.index)).perform(click());
            waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 3000);
        }
        for (int i = 0; i < adapter.getItemCount(); i++) {
            TestUtils.Entry<MyTreeItemAdapter.WrappedItem> entry = find(adapter.getItems(), MyTreeItemAdapter.WrappedItem.class, i);
            if (entry.object.children.size() == 0) {
                continue;
            }
            onView(withId(R.id.recycler)).perform(scrollToPosition(entry.index));
            onView(withIdInRecyclerView(R.id.check_opened, R.id.recycler, entry.index)).perform(click());
            waitForIdlingResource(mRecyclerViewAnimatingIdlingResource, 3000);
        }
    }

    @Test
    public void clickAll() {
        TreeItemAdapterExampleActivity activity = mActivityTestRule.getActivity();
        MyTreeItemAdapter adapter = (MyTreeItemAdapter) activity.mRecyclerView.getAdapter();

        activity.mSnackbarLogic = spy(new MockSnackbarLogic());

        for (int i = 0; i < adapter.getItemCount(); i++) {
            reset(activity.mSnackbarLogic);
            MyTreeItem item = adapter.getItemAt(i).item;

            onView(withId(R.id.recycler)).perform(scrollToPosition(i));
            onView(withIdInRecyclerView(R.id.text_label, R.id.recycler, i)).perform(click());

            verify(activity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(containsString(item.getText())),
                    anyInt());
        }
    }
}
