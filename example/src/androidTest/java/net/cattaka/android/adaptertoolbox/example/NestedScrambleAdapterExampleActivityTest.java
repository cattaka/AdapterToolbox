package net.cattaka.android.adaptertoolbox.example;

import android.content.res.Resources;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.data.NestedScrambleInfo;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils.Entry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
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
public class NestedScrambleAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<NestedScrambleAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(NestedScrambleAdapterExampleActivity.class, false, false);

    NestedScrambleAdapterExampleActivity mActivity;
    ScrambleAdapter<Object> mAdapter;

    @Before
    public void before() {
        mActivity = mActivityTestRule.launchActivity(null);
        mAdapter = mActivity.mAdapter;
        mActivity.mSnackbarLogic = spy(new SnackbarLogic());
    }

    @Test
    public void scrollAll() {
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.recycler)).perform(scrollToPosition(i));
            onView(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, i)).perform(ViewActions.swipeLeft());
        }
    }

    @Test
    public void clickNestedItem_numberItem() {
        int parentPosition = 10;
        onView(withId(R.id.recycler)).perform(scrollToPosition(parentPosition));

        NestedScrambleInfo parentItem = (NestedScrambleInfo) mAdapter.getItemAt(parentPosition);
        {   // Click Number item
            Entry<Number> entry = find(parentItem.getItems(), Number.class, 0);
            onView(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition)).perform(scrollToPosition(entry.index));
            onView(allOf(
                    withIdInRecyclerView(R.id.text, R.id.recycler_nested, entry.index),
                    isDescendantOfA(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition))
            )).perform(click());

            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(containsString("Row=" + parentPosition), containsString(String.valueOf(entry.object)))),
                    anyInt()
            );
        }
    }

    @Test
    public void clickNestedItem_stringItem() {
        int parentPosition = 10;
        onView(withId(R.id.recycler)).perform(scrollToPosition(parentPosition));

        NestedScrambleInfo parentItem = (NestedScrambleInfo) mAdapter.getItemAt(parentPosition);
        {   // Click String item
            Entry<String> entry = find(parentItem.getItems(), String.class, 0);
            onView(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition)).perform(scrollToPosition(entry.index));
            onView(allOf(
                    withIdInRecyclerView(R.id.text, R.id.recycler_nested, entry.index),
                    isDescendantOfA(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition))
            )).perform(click());

            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(containsString("Row=" + parentPosition), containsString(String.valueOf(entry.object)))),
                    anyInt()
            );
        }
    }

    @Test
    public void clickNestedItem_ordinalLabelItem() {
        int parentPosition = 10;
        onView(withId(R.id.recycler)).perform(scrollToPosition(parentPosition));

        NestedScrambleInfo parentItem = (NestedScrambleInfo) mAdapter.getItemAt(parentPosition);
        {   // Click code of OrdinalLabel item
            Entry<OrdinalLabel> entry = find(parentItem.getItems(), OrdinalLabel.class, 0);
            onView(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition)).perform(scrollToPosition(entry.index));
            onView(allOf(
                    withIdInRecyclerView(R.id.text_code, R.id.recycler_nested, entry.index),
                    isDescendantOfA(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition))
            )).perform(click());

            Resources res = mActivity.getResources();
            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(
                            containsString("Row=" + parentPosition),
                            containsString("code of "),
                            containsString(String.valueOf(entry.object.getCode())),
                            containsString(String.valueOf(entry.object.getLabel(res)))
                    )),
                    anyInt()
            );
        }
        {   // Click code of OrdinalLabel item
            Entry<OrdinalLabel> entry = find(parentItem.getItems(), OrdinalLabel.class, 0);
            onView(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition)).perform(scrollToPosition(entry.index));
            onView(allOf(
                    withIdInRecyclerView(R.id.text_label, R.id.recycler_nested, entry.index),
                    isDescendantOfA(withIdInRecyclerView(R.id.recycler_nested, R.id.recycler, parentPosition))
            )).perform(click());

            Resources res = mActivity.getResources();
            verify(mActivity.mSnackbarLogic).make(
                    any(View.class),
                    argThat(allOf(
                            containsString("Row=" + parentPosition),
                            containsString("label of "),
                            containsString(String.valueOf(entry.object.getCode())),
                            containsString(String.valueOf(entry.object.getLabel(res)))
                    )),
                    anyInt()
            );
        }
    }
}
